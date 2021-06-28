import { Moment } from 'moment';

export interface IPayment {
  id?: number;
  date?: string;
  amount?: number;
}

export const defaultValue: Readonly<IPayment> = {};
