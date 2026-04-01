#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ENV_FILE="${ROOT_DIR}/orthanc/sync.env"
LOG_DIR="${ROOT_DIR}/logs"
LOCK_FILE="${LOG_DIR}/sync_orthanc_mysql.lock"
PHP_BIN="${PHP_BIN:-php}"
CONFIG_PATH="${CONFIG_PATH:-${ROOT_DIR}/orthanc/mysql.json}"

mkdir -p "${LOG_DIR}"

if [[ -f "${ENV_FILE}" ]]; then
  # shellcheck disable=SC1090
  source "${ENV_FILE}"
fi

exec 9>"${LOCK_FILE}"
if command -v flock >/dev/null 2>&1; then
  flock -n 9 || {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] sync_orthanc_mysql masih berjalan, skip."
    exit 0
  }
fi

exec "${PHP_BIN}" "${ROOT_DIR}/scripts/sync_orthanc_mysql.php" --config="${CONFIG_PATH}"
