#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BACKEND_LOG="$ROOT_DIR/.dev-backend.log"

cd "$ROOT_DIR"

echo "Starting MongoDB..."
docker compose up -d mongo

if [[ ! -d "$ROOT_DIR/frontend/node_modules" ]]; then
  echo "Installing frontend dependencies..."
  (
    cd "$ROOT_DIR/frontend"
    npm install
  )
fi

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
npm run dev -- --host
