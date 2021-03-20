import { Moment } from 'moment';

export interface IBlog {
  id?: number;
  title?: string;
  publishDate?: string;
  titleImgUrl?: string;
  content?: string;
}

export const defaultValue: Readonly<IBlog> = {};
