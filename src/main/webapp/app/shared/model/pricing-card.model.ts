import { IPricingContent } from 'app/shared/model/pricing-content.model';

export interface IPricingCard {
  id?: number;
  name?: string;
  price?: number;
  pricingContents?: IPricingContent[];
  serviceItemName?: string;
  serviceItemId?: number;
  imgUrlName?: string;
  imgUrlId?: number;
  file?: File;
}

export const defaultValue: Readonly<IPricingCard> = {};
