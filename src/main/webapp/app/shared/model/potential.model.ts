import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPotential {
  id?: number;
  name?: string;
  phone?: number;
  gender?: Gender;
  serviceName?: string;
  serviceId?: number;
  branchAdress?: string;
  branchId?: number;
}

export const defaultValue: Readonly<IPotential> = {};
