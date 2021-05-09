import { IServiceItem } from 'app/shared/model/service-item.model';

export interface IProcess {
  id?: number;
  name?: string;
  order?: number;
  process?: IServiceItem;
}

export const defaultValue: Readonly<IProcess> = {};
