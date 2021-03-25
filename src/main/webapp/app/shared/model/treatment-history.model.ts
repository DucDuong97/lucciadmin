import { Moment } from 'moment';
import { ITreatment } from 'app/shared/model/treatment.model';

export interface ITreatmentHistory {
  id?: number;
  date?: string;
  treatment?: ITreatment;
}

export const defaultValue: Readonly<ITreatmentHistory> = {};
