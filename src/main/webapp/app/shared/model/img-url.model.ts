import { IServiceItem } from 'app/shared/model/service-item.model';

export interface IImgUrl {
  id?: number;
  imgUrl?: string;
  serviceItem?: IServiceItem;
  imageFile?: File | Blob;
}

export const defaultValue: Readonly<IImgUrl> = {};
