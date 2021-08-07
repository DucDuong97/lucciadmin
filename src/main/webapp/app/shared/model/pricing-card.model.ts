import { IPricingContent } from 'app/shared/model/pricing-content.model';

export interface IPricingCard {
  id?: number;
  name?: string;
  price?: number;
  pricingContents?: IPricingContent[];
  serviceItemName?: string;
  serviceItemId?: number;
}

export const defaultValue: Readonly<IPricingCard> = {};
