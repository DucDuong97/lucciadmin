import { IServiceItem } from 'app/shared/model/service-item.model';

export interface IServiceOption {
  id?: number;
  name?: string;
  benefits?: string;
  price?: number;
  service?: IServiceItem;
}

export const defaultValue: Readonly<IServiceOption> = {};
