import { IPricingContent } from 'app/shared/model/pricing-content.model';
import { IServiceItem } from 'app/shared/model/service-item.model';

export interface IPricingCard {
  id?: number;
  name?: string;
  price?: number;
  contents?: IPricingContent[];
  serviceItem?: IServiceItem;
}

export const defaultValue: Readonly<IPricingCard> = {};
