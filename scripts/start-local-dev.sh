#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BACKEND_LOG="$ROOT_DIR/.dev-backend.log"

cd "$ROOT_DIR"

stop_repo_processes() {
  mapfile -t backend_pids < <(pgrep -f "com.example.demo.DemoApplication" || true)
  mapfile -t frontend_pids < <(pgrep -f "$ROOT_DIR/frontend.*vite" || true)

  if [[ ${#backend_pids[@]} -gt 0 ]]; then
    echo "Stopping previous Spring Boot process: ${backend_pids[*]}"
    kill "${backend_pids[@]}" >/dev/null 2>&1 || true
  fi

  if [[ ${#frontend_pids[@]} -gt 0 ]]; then
    echo "Stopping previous Vite process: ${frontend_pids[*]}"
    kill "${frontend_pids[@]}" >/dev/null 2>&1 || true
  fi
}

ensure_port_free() {
  local port="$1"
  if ss -ltn "( sport = :$port )" | tail -n +2 | grep -q .; then
    echo "Port $port is still in use."
    echo "Free it manually, then run this script again."
    exit 1
  fi
}

if [[ ! -d "$ROOT_DIR/frontend/node_modules" ]]; then
  echo "Installing frontend dependencies..."
  (
    cd "$ROOT_DIR/frontend"
    npm install
  )
fi

stop_repo_processes
sleep 1
ensure_port_free 8080
ensure_port_free 5173

echo "Starting Spring Boot backend..."
./gradlew bootRun >"$BACKEND_LOG" 2>&1 &
BACKEND_PID=$!

cleanup() {
  if kill -0 "$BACKEND_PID" >/dev/null 2>&1; then
    echo
    echo "Stopping Spring Boot backend..."
    kill "$BACKEND_PID" >/dev/null 2>&1 || true
    wait "$BACKEND_PID" 2>/dev/null || true
  fi
}

trap cleanup EXIT INT TERM

echo "Waiting for backend on http://localhost:8080 ..."
for _ in {1..60}; do
  if curl -fsS http://localhost:8080/actuator/health >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

if ! curl -fsS http://localhost:8080/actuator/health >/dev/null 2>&1; then
  echo "Backend did not become ready in time."
  echo "Check the backend log: $BACKEND_LOG"
  exit 1
fi

echo "Backend ready."
echo "Frontend: http://localhost:5173"
echo "Backend:  http://localhost:8080"
echo "Swagger:  http://localhost:8080/swagger-ui.html"
echo "Backend log: $BACKEND_LOG"
echo

cd "$ROOT_DIR/frontend"
npm run dev -- --host 127.0.0.1 --strictPort
