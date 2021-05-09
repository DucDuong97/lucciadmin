import { Moment } from 'moment';
import { IImgUrl } from 'app/shared/model/img-url.model';
import { IServiceItem } from 'app/shared/model/service-item.model';

export interface IBlog {
  id?: number;
  title?: string;
  publishDate?: string;
  content?: string;
  titleImgUrl?: IImgUrl;
  relatedBlog?: IServiceItem;
}

export const defaultValue: Readonly<IBlog> = {};
