import { ITreatment } from 'app/shared/model/treatment.model';

export interface ITreatmentPlan {
  id?: number;
  presentComplaint?: string;
  pastMedicalHistory?: string;
  note?: string;
  customerName?: string;
  customerId?: number;
  serviceName?: string;
  serviceId?: number;
  treatments?: ITreatment[];
}

export const defaultValue: Readonly<ITreatmentPlan> = {};
