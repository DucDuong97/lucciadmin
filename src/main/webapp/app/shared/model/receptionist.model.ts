import { IPerson } from 'app/shared/model/person.model';
import { IPayment } from 'app/shared/model/payment.model';
import { IGetMaterial } from 'app/shared/model/get-material.model';

export interface IReceptionist {
  id?: number;
  salary?: number;
  person?: IPerson;
  payments?: IPayment[];
  getMaterials?: IGetMaterial[];
}

export const defaultValue: Readonly<IReceptionist> = {};
