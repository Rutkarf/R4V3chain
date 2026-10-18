/** Build production — Cloudflare utilise environment.cloudflare.ts via ng build --configuration cloudflare. */
import { buildEnvironment } from './environment.factory';

export const environment = buildEnvironment({
  production: true,
  apiUrl: 'https://r4v3chain-backend-1-0-0.onrender.com/api',
  liveWsUrl: 'wss://r4v3chain-backend-1-0-0.onrender.com/ws/live',
  chatWsUrl: 'wss://r4v3chain-backend-1-0-0.onrender.com/ws/chat',
});
