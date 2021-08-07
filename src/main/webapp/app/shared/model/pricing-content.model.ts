export interface IPricingContent {
  id?: number;
  content?: string;
  pro?: boolean;
  pricingCardName?: string;
  pricingCardId?: number;
}

export const defaultValue: Readonly<IPricingContent> = {
  pro: false,
};
