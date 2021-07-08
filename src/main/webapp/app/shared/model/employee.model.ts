import { Moment } from 'moment';
import { IBranch } from 'app/shared/model/branch.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { EmployeeRole } from 'app/shared/model/enumerations/employee-role.model';

export interface IEmployee {
  id?: number;
  name?: string;
  phone?: string;
  address?: string;
  birth?: string;
  gender?: Gender;
  role?: EmployeeRole;
  salary?: number;
  workAt?: IBranch;
}

export const defaultValue: Readonly<IEmployee> = {};
