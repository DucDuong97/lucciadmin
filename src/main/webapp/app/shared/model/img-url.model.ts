import { IServiceItem } from 'app/shared/model/service-item.model';
import { ITreatment } from 'app/shared/model/treatment.model';

export interface IImgUrl {
  id?: number;
  name?: string;
  path?: string;
  imageFile?: File | Blob;
  serviceItems?: IServiceItem[];
  treatments?: ITreatment[];
}

export const defaultValue: Readonly<IImgUrl> = {};
