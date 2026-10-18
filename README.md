<div align="center">

```
╔══════════════════════════════════════════════════════════════╗
║                                                              ║
║     ██████╗ ██╗  ██╗██╗   ██╗██████╗  ██████╗██╗  ██╗       ║
║     ██╔══██╗██║  ██║██║   ██║╚════██╗██╔════╝██║  ██║       ║
║     ██████╔╝███████║██║   ██║ █████╔╝██║     ███████║       ║
║     ██╔══██╗╚════██║╚██╗ ██╔╝ ╚═══██╗██║     ██╔══██║       ║
║     ██║  ██║     ██║ ╚████╔╝ ██████╔╝╚██████╗██║  ██║       ║
║     ╚═╝  ╚═╝     ╚═╝  ╚═══╝  ╚═════╝  ╚═════╝╚═╝  ╚═╝       ║
║                                                              ║
║            ◈  PEER ACCOUNTING PROTOCOL  ◈                    ║
║         R4V3  ·  M4T3R  ·  CAN0PY  ·  SIGNAL                 ║
║                                                              ║
╚══════════════════════════════════════════════════════════════╝
```

# R4V3chain

**Protocole de comptabilité peer-to-peer** — transferer de la valeur **sans institution** pour autoriser chaque paiement.

[![Demo](https://img.shields.io/badge/◈_DEMO-r4v3chain.pages.dev-0A1220?style=for-the-badge&labelColor=2ec4d6)](https://r4v3chain.pages.dev)
[![API](https://img.shields.io/badge/API-health-235789?style=for-the-badge&logo=springboot&logoColor=white)](https://r4v3chain-backend-1-0-0.onrender.com/api/health)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](#-07--stack)
[![Angular](https://img.shields.io/badge/Angular-21-DD0031?style=for-the-badge&logo=angular&logoColor=white)](#-07--stack)
[![Spring](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](#-07--stack)

```
◆ Don't Sell Ur R4V3  ◆  Dreamz In Glitch , H0LD The Sawt  ◆
```

| | |
|:--|:--|
| **◈ Vitre démo** | [r4v3chain.pages.dev](https://r4v3chain.pages.dev) |
| **◈ API** | [`/api/health`](https://r4v3chain-backend-1-0-0.onrender.com/api/health) |
| **◈ Opérateur** | RJHBZ · SAS R4V3chain |
| **◈ Canon** | White Paper 0.1.0 · Roadmap N1 · FAITS 001 |
| **◈ Signal** | **31 / 01 / 2027** · France · Tag LORA + Tag ionosphérique |

> **Ce n’est pas une crypto réelle.** Pas de mainnet, pas de custodian, pas d’achat, **aucun yield garanti**.  
> Jetons de démonstration uniquement. Peg intention ≠ valuation démo. Document **non audité** · **pas une offre de titres**.

</div>

---

## ◈ Sommaire

| | | |
|:--|:--|:--|
| [01 · Vision](#-01--vision--problème) | [02 · Tokenomics](#-02--tokenomics--faits-dossier) | [03 · Protocole](#-03--protocole--os-du-paper) |
| [04 · Phases](#-04--phases-signal--city) | [05 · Roadmap](#-05--roadmap-n1--jalons) | [06 · Démo](#-06--démo-produit-ce-repo) |
| [07 · Stack](#-07--stack) | [08 · Quick start](#-08--quick-start) | [09 · Structure](#-09--structure-du-monorepo) |
| [10 · Disclaimer](#-10--disclaimer--serment) | | |

---

## ◈ 01 — Vision & problème

```
┌─ TRANSMISSION ─────────────────────────────────────────────┐
│  Le commerce numérique s’appuie encore sur des institutions │
│  qui valident, annulent et médiatisent.                     │
│  → coûts · friction · exclusion                             │
│                                                             │
│  Sans anti-double-dépense, un expéditeur peut créditer      │
│  deux destinataires avec le même solde.                     │
└─────────────────────────────────────────────────────────────┘
```

**R4V3chain** propose :

| Pilier | Rôle |
|:-------|:-----|
| ◆ **État ordonné** | Comptes + nonces + `apply(S, tx)` atomique |
| ◆ **Frais lisibles** | Grille **CAN0PY** — anti-spam, pas de gas opaque |
| ◆ **Bootstrap explicite** | Phase Signal : centralisation **assumée et déclarée** |
| ◆ **Faits dossier** | Burn créateur · capital SAS · peg **intention** |

> *Chez les Extracteurs le gas danse pour extraire. Ici le prix du passage est fixe.*

**Cinq Portes** (canon) : Token · Poussière · Feu · Mur · Vitre

```
FAITS 001  ⟩  White Paper  ⟩  posts
     ▲
 divergence = correction obligatoire
```

---

## ◈ 02 — Tokenomics & faits dossier

| Clé | Valeur |
|:----|:-------|
| ◆ Token | **R4V3** |
| ◆ Subunit / frais | **M4T3R** (26 decimals) |
| ◆ Frais tx | **CAN0PY** `fee(n) = 1 + 10 × ⌊(n−1)/10⌋` M4T3R |
| ◆ 1ʳᵉ mine | **1 M4T3R** |
| ◆ ChronoBoost | 26 → 8 decimals · −1d / cycle 31 Jan · → 8 (= BTC) |
| ◆ Split fees | **49 / 49 / 1 / 1** |
| ◆ Burn créateur | **2,6 % / an** (bag créateur · OUVERT) |
| ◆ Peg dossier | Intention **1 CHF ≈ 1 R4V3** — **≠ garantie** |
| ◆ Peg démo | Ludique UI — **≠ offre** |
| ◆ Capital SAS | **10 000 CHF** |
| ◆ Vesting | Aucun |
| ◆ Emission cachée | **Interdite** |

### Règle dure CAN0PY

```
fee(n) = 1 + 10 × floor((n − 1) / 10)   [M4T3R]
───────────────────────────────────────
n = 1       →  1 M4T3R
n = 2…10    →  1 M4T3R
n = 11…20   → 11 M4T3R
…
Toute tx valide paie exactement ce quantum.
```

### Transaction formelle

```
tx = (from, to, value, nonce, fee=CAN0PY, sig)

valid iff:
  ✓ champs + fee CAN0PY + sig(from)
  ✓ nonce == nonce[from] + 1
  ✓ balance[from] ≥ value + fee
sinon → rejet · S inchangé
```

---

## ◈ 03 — Protocole · os du paper

| Module | Essence |
|:-------|:--------|
| ◆ Blocs | `prev_hash` · Merkle · timestamp · producteurs |
| ◆ Consensus | Fork choice = poids maximal (Phase City) |
| ◆ Network | Gossip best-effort · sync chaîne canonique |
| ◆ État | `S' = apply(S, tx) \| reject` |
| ◆ Privacy | Pseudonymat public — **pas** d’anonymat magique |
| ◆ Light clients | Headers + preuve Merkle (SPV-like) |
| ◆ Incentive | Fee pressure + burn créateur · pas d’émission inventée |

**Héritage** : horodatage / Merkle / anti-double-spend (BTC) · vision machine d’état (ETH).  
**Différenciateur** : comptes + frais fixes M4T3R + bootstrap documenté.

---

## ◈ 04 — Phases Signal → City

```
     SIGNAL                          CITY
  ┌────────────┐               ┌──────────────────┐
  │ Bootstrap  │  ──────────▶  │ Multi-nœuds      │
  │ RJHBZ      │               │ poids honnête    │
  │ déclaré    │               │ > attaquants     │
  └────────────┘               └──────────────────┘
        ▲                              ▲
   vitre démo                    replication
   (ce repo)                     P2P / BFT path
```

| Phase | Signification |
|:------|:--------------|
| ◈ **Signal** | Validateur(s) documentés par RJHBZ — risque élevé **annoncé** |
| ◈ **City** | Majority of honest weight · sans tiers bancaire pour trancher les soldes |

---

## ◈ 05 — Roadmap N1 · jalons

<div align="center">

**H0LL0W NATI0N** · MESH PATH · ladder ×26 · 100 phases  
Premier signal public ▸ **31 janvier 2027**

</div>

### Cap (canon)

| | |
|:--|:--|
| ◆ Cibles | **10 000 nœuds** · jusqu’à **1 000 000 wallets** |
| ◆ Planif. | jusqu’à **26 000 000 CHF** cumulés (seuil lore — **pas** un jackpot) |
| ◆ DIY | 10 NODES · CIRCUIT H0LL0W · SAWT |
| ◆ Interdit | Garantir un bénéfice |

### Livre des treize (jalons 01 → 13)

| # | Jalon |
|--:|:------|
| 01 | Inventaire dépôts / ruines du code |
| 02 | Registre monétaire + ChronoBoost spécifié |
| 03 | Mempool Squad demo (wss) |
| 04 | SAWT Tag LORA lab |
| 05 | Tag ionosphérique / EME doc |
| 06 | ESL prototype #1 |
| 07 | Mine ONMineur prototype #1 |
| 08 | Testnet sans valeur |
| 09 | Initiation ONG 1 centime |
| 10 | Charte ASCII + Luluw |
| 11 | CIRCUIT H0LL0W pilote |
| 12 | Pentest / résilience |
| 13 | Répétition générale signal 31/01 |

### Jalons 14 → 26

| # | Jalon |
|--:|:------|
| 14 | **Premier signal public 31/01/2027** (LORA + iono) |
| 15 | Ouverture C1TAD3L.Z0NES #1 |
| 16 | Mainnet utilitaire (go/no-go) |
| 17 | ChronoBoost #1 (2028) |
| 18 | Apps lore (RES1LIENCE / B00K ME B1TCH) |
| 19 | Moon Bounce mesuré |
| 20 | Checkpoint 10 000 nœuds |
| 21 | PEG architecture auditable |
| 22 | Pack kit-korp scale |
| 23 | Quêtes éclipse live |
| 24 | Market / ressources 26 M |
| 25 | CyberSpace Star Conquest |
| 26 | Miroir document / ERA path |

```
Épitaphe si échec :
« nous avons tenu le signal sans vendre le rêve. »
```

### Axes organisation

| Axe | Entité |
|:----|:-------|
| 1 | SASU PI R4V3 — PI · PEG · ChronoBoost |
| 2 | RES1LIENCE — paiements |
| 3 | B00K ME B1TCH — social / resa |
| 4 | **SAS R4V3chain** — hardware + chaîne + demos |
| 5 | ONG H0LL0W R4V3 NATI0N — culture · initiation |

---

## ◈ 06 — Démo produit (ce repo)

Vitre full-stack — shell **250×550** :

| Zone | Contenu |
|:-----|:--------|
| ◆ **MetaVerseBB** | Jumeau 3D Marseille (OSM + Three.js) · champ M4T3R |
| ◆ **Wallet / Faucet** | Création, claim testnet, explorer |
| ◆ **Chaîne** | Blocs · mempool · peers P2P |
| ◆ **Swap / LaunchLab** | Quote pédagogique |
| ◆ **Showcase** | Hub R4V3 · chat · DAO preview · market |
| ◆ **Quêtes** | Progression démo · XP |

```
Navigateur ──/api /ws──▶ Spring Boot ──▶ Postgres | memory
 localhost:4200              :8080
 Pages · Render
```

Chaîne démo : **chain-id 3377** · réseau *R4V3chain Native* · token **R4V3**.

---

## ◈ 07 — Stack

| Couche | Tech |
|:-------|:-----|
| ◆ Frontend | Angular 21 · Three.js · projet `R4V3` |
| ◆ Backend | Spring Boot 3.5 · Java 21 · `io.r4v3chain` |
| ◆ Data | Memory (dev) · PostgreSQL 16 |
| ◆ Realtime | WebSockets live / chat / peers / arena |
| ◆ Ops | Docker Compose · CI · Cloudflare · Render |

Env : préfixe **`R4V3CHAIN_*`** · Spring **`r4v3chain.*`**

---

## ◈ 08 — Quick start

### Prérequis

`Node 22+` · `Java 21+` · Docker optionnel

### Frontend

```bash
cd apps/r4v3chain-frontend/R4V3
npm install
npm start
# → http://localhost:4200
```

### Backend (memory)

```bash
cd apps/r4v3chain-backend
export R4V3CHAIN_PERSISTENCE_MODE=memory
export R4V3CHAIN_JWT_SECRET=dev-local-jwt-secret-for-cursor-development-only-32
./mvnw spring-boot:run
# → http://localhost:8080/api/health
```

### Compose

```bash
cd apps/r4v3chain-backend
npm run secrets:generate
docker compose --profile dev up --build -d
```

---

## ◈ 09 — Structure du monorepo

```
R4V3chain/
├── apps/
│   ├── r4v3chain-frontend/R4V3/   ◈ Angular SPA
│   └── r4v3chain-backend/         ◈ Spring API
├── deploy/                        ◈ Render · Cloudflare · INFRA
├── docs/                          ◈ brand · UML
├── infra/nginx/
├── docker-compose.yml
└── README.md
```

Hub pédagogique compagnon → **[R4V3chainReview](https://github.com/Rutkarf/R4V3chainReview)**

---

## ◈ 10 — Disclaimer & serment

```
┌─ SERMENT ──────────────────────────────────────────────────┐
│  Je n’achète pas un trône. Je tiens un signal.             │
│  Je distingue rêve et réserve, glitch et preuve.           │
│  Je ne garantis aucun bénéfice.                            │
│  Je défends le PEG tant que le bag créateur respire        │
│  (−2,6 % / an redistribués dans la Nation).                │
└────────────────────────────────────────────────────────────┘
```

- Document de travail **non audité**
- **Pas** une offre de titres
- Données Marseille = footprints OSM (ODbL), pas un levé IGN
- Attribution OSM : © contributeurs [OpenStreetMap](https://www.openstreetmap.org/copyright)

<div align="center">

```
◈  Wake the tag. Hold the signal. Don't sell the dream short.  ◈
```

**v0.1.0** · RJHBZ · SAS R4V3chain · Pre-Seal

[Website](https://r4v3chain.pages.dev) · [Review hub](https://github.com/Rutkarf/R4V3chainReview) · [Issues](https://github.com/Rutkarf/R4V3chain/issues)

</div>
