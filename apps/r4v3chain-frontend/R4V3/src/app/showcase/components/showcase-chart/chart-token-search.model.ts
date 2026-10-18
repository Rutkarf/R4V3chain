export type ChartSearchSource = 'coingecko' | 'geckoterminal' | 'launchlab' | 'r4v3chain';

export interface ChartSearchResult {
  id: string;
  symbol: string;
  name: string;
  thumb: string;
  source?: ChartSearchSource;
  network?: string;
}

export function chartSearchSourceLabel(source?: ChartSearchSource, network?: string): string {
  switch (source) {
    case 'geckoterminal':
      return network?.trim() || 'DEX';
    case 'launchlab':
      return 'Laboratoire';
    case 'r4v3chain':
      return 'R4V3';
    default:
      return 'CoinGecko';
  }
}
