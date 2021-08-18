import { Moment } from 'moment';

export interface IBooking {
  id?: number;
  date?: string;
  time?: string;
  correspondDoctorName?: string;
  correspondDoctorId?: number;
  customerName?: string;
  customerId?: number;
  treatmentPlanId?: number;
  branchAdress?: string;
  branchId?: number;
}

export const defaultValue: Readonly<IBooking> = {};
