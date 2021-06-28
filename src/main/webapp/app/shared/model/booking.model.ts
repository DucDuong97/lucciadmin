import { Moment } from 'moment';

export interface IBooking {
  id?: number;
  date?: string;
  time?: string;
  branch?: string;
}

export const defaultValue: Readonly<IBooking> = {};
