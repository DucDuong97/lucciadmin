import { Moment } from 'moment';
import { IDoctor } from 'app/shared/model/doctor.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IBooking {
  id?: number;
  date?: string;
  hasPurchase?: boolean;
  doctor?: IDoctor;
  patient?: IPatient;
}

export const defaultValue: Readonly<IBooking> = {
  hasPurchase: false,
};
