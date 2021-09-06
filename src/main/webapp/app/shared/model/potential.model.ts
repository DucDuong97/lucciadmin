import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPotential {
  id?: number;
  name?: string;
  phone?: number;
  gender?: Gender;
  time?: string;
  serviceName?: string;
  serviceId?: number;
  branchAdress?: string;
  branchId?: number;
}

export const defaultValue: Readonly<IPotential> = {};
