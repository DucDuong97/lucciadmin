import { IPerson } from 'app/shared/model/person.model';

export interface IAccountant {
  id?: number;
  salary?: number;
  person?: IPerson;
}

export const defaultValue: Readonly<IAccountant> = {};
