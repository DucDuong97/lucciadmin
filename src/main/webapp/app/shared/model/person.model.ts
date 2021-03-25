import { IDoctor } from 'app/shared/model/doctor.model';
import { INurse } from 'app/shared/model/nurse.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IReceptionist } from 'app/shared/model/receptionist.model';
import { IAccountant } from 'app/shared/model/accountant.model';

export interface IPerson {
  id?: number;
  name?: string;
  phone?: string;
  adress?: string;
  doctor?: IDoctor;
  nurse?: INurse;
  patient?: IPatient;
  receptionist?: IReceptionist;
  accountant?: IAccountant;
}

export const defaultValue: Readonly<IPerson> = {};
