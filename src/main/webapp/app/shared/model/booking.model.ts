import { Moment } from 'moment';

export interface IBooking {
  id?: number;
  date?: string;
  time?: string;
  correspondDoctorId?: number;
  customerId?: number;
  treatmentPlanId?: number;
  branchId?: number;
}

export const defaultValue: Readonly<IBooking> = {};
