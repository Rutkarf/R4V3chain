#!/usr/bin/env bash
# Génère des secrets aléatoires pour .env (sans écraser un fichier existant).
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
REPO_ROOT="$(cd "${BACKEND_DIR}/../.." && pwd)"
ENV_FILE="${1:-${REPO_ROOT}/.env}"

if [[ -f "${ENV_FILE}" ]]; then
  echo "Fichier existant : ${ENV_FILE} (aucune modification)" >&2
  echo "Supprimez-le ou passez un autre chemin pour régénérer." >&2
  exit 0
fi

random_hex() {
  if command -v openssl >/dev/null 2>&1; then
    openssl rand -hex "${1}"
  else
    head -c "$(( ${1} / 2 ))" /dev/urandom | od -An -tx1 | tr -d ' \n'
  fi
}

POSTGRES_PASSWORD="$(random_hex 24)"
JWT_SECRET="$(random_hex 32)"
ACTUATOR_TOKEN="$(random_hex 32)"

cat > "${ENV_FILE}" <<EOF
# Généré par apps/r4v3chain-backend/bin/generate-secrets.sh
POSTGRES_DB=r4v3chain
POSTGRES_USER=r4v3chain
POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
R4V3CHAIN_JWT_SECRET=${JWT_SECRET}
R4V3CHAIN_ACTUATOR_TOKEN=${ACTUATOR_TOKEN}
R4V3CHAIN_CORS_EXTRA=
APP_PORT=8080
APP_TLS_PORT=443
APP_STAGING_PORT=9080
POSTGRES_STAGING_PORT=5433
POSTGRES_DEV_PORT=5432
BACKEND_DEV_PORT=8080
JAVA_TOOL_OPTIONS=-Xmx512m
EOF

chmod 600 "${ENV_FILE}"
echo "Secrets écrits dans ${ENV_FILE}"
