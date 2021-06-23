import { IImgUrl } from 'app/shared/model/img-url.model';

export interface ICustomerReview {
  id?: number;
  customerName?: string;
  customerAddress?: string;
  comment?: string;
  customerImgUrl?: IImgUrl;
  file?: File;
}

export const defaultValue: Readonly<ICustomerReview> = {};
