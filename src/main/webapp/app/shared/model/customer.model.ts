import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { CustomerTier } from 'app/shared/model/enumerations/customer-tier.model';

export interface ICustomer {
  id?: number;
  name?: string;
  phone?: string;
  address?: string;
  birth?: string;
  gender?: Gender;
  tier?: CustomerTier;
  newCustomer?: boolean;
  correspondConsultant?: IEmployee;
}

export const defaultValue: Readonly<ICustomer> = {
  newCustomer: false,
};
