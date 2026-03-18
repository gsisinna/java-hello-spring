#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

mapfile -t backend_pids < <(pgrep -f "com.example.demo.DemoApplication" || true)
mapfile -t frontend_pids < <(pgrep -f "$ROOT_DIR/frontend.*vite" || true)

if [[ ${#backend_pids[@]} -eq 0 && ${#frontend_pids[@]} -eq 0 ]]; then
  echo "No repo dev processes found."
  exit 0
fi

if [[ ${#backend_pids[@]} -gt 0 ]]; then
  echo "Stopping Spring Boot process: ${backend_pids[*]}"
  kill "${backend_pids[@]}" >/dev/null 2>&1 || true
fi

if [[ ${#frontend_pids[@]} -gt 0 ]]; then
  echo "Stopping Vite process: ${frontend_pids[*]}"
  kill "${frontend_pids[@]}" >/dev/null 2>&1 || true
fi
