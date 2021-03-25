import { Moment } from 'moment';
import { IBooking } from 'app/shared/model/booking.model';
import { IReceptionist } from 'app/shared/model/receptionist.model';

export interface IPayment {
  id?: number;
  date?: string;
  amount?: number;
  booking?: IBooking;
  receptionist?: IReceptionist;
}

export const defaultValue: Readonly<IPayment> = {};
