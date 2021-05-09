import { IImgUrl } from 'app/shared/model/img-url.model';

export interface IAchievement {
  id?: number;
  name?: string;
  number?: number;
  imgUrl?: IImgUrl;
}

export const defaultValue: Readonly<IAchievement> = {};
