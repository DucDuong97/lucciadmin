import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { EmployeeRole } from 'app/shared/model/enumerations/employee-role.model';

export interface IEmployee {
  id?: number;
  name?: string;
  phone?: number;
  address?: string;
  birth?: string;
  gender?: Gender;
  role?: EmployeeRole;
  workAtAdress?: string;
  workAtId?: number;
}

export const defaultValue: Readonly<IEmployee> = {};
