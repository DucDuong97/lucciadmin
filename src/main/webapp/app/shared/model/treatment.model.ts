import { Moment } from 'moment';
import { IImgUrl } from 'app/shared/model/img-url.model';

export interface ITreatment {
  id?: number;
  description?: string;
  date?: string;
  nextPlan?: string;
  revisitDate?: string;
  doctorName?: string;
  doctorId?: number;
  treatmentImgUrls?: IImgUrl[];
  treatmentPlanId?: number;
}

export const defaultValue: Readonly<ITreatment> = {};
