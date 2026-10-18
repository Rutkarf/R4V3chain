# Migration infra — hosts R4V3chain

Le code local utilise déjà le nom **R4V3chain** (`R4V3CHAIN_*`, `io.r4v3chain`, dossiers `R4V3chain`).

## Cibles

| Ressource | Valeur cible |
|-----------|----------------|
| Cloudflare Pages | `r4v3chain.pages.dev` / Wrangler `name = "r4v3chain"` |
| API Render | `r4v3chain-backend-1-0-0.onrender.com` (ou alias `r4v3chain-backend.onrender.com`) |
| Docker Hub | `docker.io/rutkarf/r4v3chain-backend` |
| GitHub | `github.com/Rutkarf/R4V3chain` |

## Checklist cutover

1. Cloudflare Pages / Wrangler — projet `r4v3chain` + DNS
2. Render — service + DB + secrets `R4V3CHAIN_*`
3. Docker Hub — publier `r4v3chain-backend`
4. Vérifier `environment.prod.ts`, `environment.cloudflare.ts`, CORS YAML, badges README
5. Smoke API + front + WebSockets `/ws/live` et `/ws/chat`

## Variables code

- Env : `R4V3CHAIN_*`
- Spring : `r4v3chain.*`
- Compose : DB/user/volumes `r4v3chain_*`
