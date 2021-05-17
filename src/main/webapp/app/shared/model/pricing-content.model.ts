import { IPricingCard } from 'app/shared/model/pricing-card.model';

export interface IPricingContent {
  id?: number;
  content?: string;
  pro?: boolean;
  pricingCard?: IPricingCard;
}

export const defaultValue: Readonly<IPricingContent> = {
  pro: false,
};
