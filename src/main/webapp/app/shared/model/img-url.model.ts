import { IServiceItem } from 'app/shared/model/service-item.model';

export interface IImgUrl {
  id?: number;
  imgUrl?: string;
  serviceItem?: IServiceItem;
}

export const defaultValue: Readonly<IImgUrl> = {};
