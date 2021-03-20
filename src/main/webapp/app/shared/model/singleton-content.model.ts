import { ContentType } from 'app/shared/model/enumerations/content-type.model';

export interface ISingletonContent {
  id?: number;
  type?: ContentType;
  content?: string;
}

export const defaultValue: Readonly<ISingletonContent> = {};
