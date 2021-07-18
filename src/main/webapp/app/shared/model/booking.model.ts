import { Moment } from 'moment';
import { BookingType } from 'app/shared/model/enumerations/booking-type.model';

export interface IBooking {
  id?: number;
  type?: BookingType;
  date?: string;
  time?: string;
  correspondDoctorId?: number;
  customerId?: number;
  branchId?: number;
}

export const defaultValue: Readonly<IBooking> = {};
