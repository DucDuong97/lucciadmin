import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  name?: string;
  phone?: number;
  address?: string;
  birth?: string;
  gender?: Gender;
  newCustomer?: boolean;
}

export const defaultValue: Readonly<ICustomer> = {
  newCustomer: true,
};
