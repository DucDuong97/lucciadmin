import { IServiceOption } from 'app/shared/model/service-option.model';

export interface IServiceItem {
  id?: number;
  name?: string;
  description?: string;
  imgUrl?: string;
  options?: IServiceOption[];
}

export const defaultValue: Readonly<IServiceItem> = {};
