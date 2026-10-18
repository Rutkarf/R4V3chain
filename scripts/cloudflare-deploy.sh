#!/usr/bin/env bash
# Déploie le frontend Angular sur Cloudflare (assets SPA via wrangler deploy).
# Prérequis : bash scripts/cloudflare-build.sh
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DIST="${ROOT}/apps/r4v3chain-frontend/R4V3/dist/browser"

cd "${ROOT}"

if [[ ! -f "${DIST}/index.html" ]]; then
  echo "FAIL: ${DIST}/index.html introuvable — lancer d'abord scripts/cloudflare-build.sh" >&2
  exit 1
fi

echo "==> Cloudflare deploy (name=r4v3chain)"
echo "    assets: ${DIST}"

npx wrangler deploy

echo "Cloudflare deploy OK"
