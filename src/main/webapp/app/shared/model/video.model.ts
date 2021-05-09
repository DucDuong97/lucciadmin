import { IServiceItem } from 'app/shared/model/service-item.model';

export interface IVideo {
  id?: number;
  url?: string;
  serviceItem?: IServiceItem;
}

export const defaultValue: Readonly<IVideo> = {};
