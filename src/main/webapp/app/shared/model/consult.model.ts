import { Moment } from 'moment';
import { IPricingCard } from 'app/shared/model/pricing-card.model';

export interface IConsult {
  id?: number;
  time?: string;
  note?: string;
  customerName?: string;
  customerId?: number;
  branchAdress?: string;
  branchId?: number;
  consultingDoctorName?: string;
  consultingDoctorId?: number;
  services?: IPricingCard[];
}

export const defaultValue: Readonly<IConsult> = {};
