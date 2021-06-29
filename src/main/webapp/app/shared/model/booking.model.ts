import { Moment } from 'moment';

export interface IBooking {
  id?: number;
  date?: string;
  time?: string;
  branch?: string;
  correspondDoctorId?: number;
  customerId?: number;
}

export const defaultValue: Readonly<IBooking> = {};
