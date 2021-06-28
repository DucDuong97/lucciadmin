import { Moment } from 'moment';

export interface IBooking {
  id?: number;
  date?: string;
  hasPurchase?: boolean;
}

export const defaultValue: Readonly<IBooking> = {
  hasPurchase: false,
};
