export interface ITreatmentPlan {
  id?: number;
  presentComplaint?: string;
  pastMedicalHistory?: string;
  note?: string;
  customerName?: string;
  customerId?: number;
  serviceName?: string;
  serviceId?: number;
}

export const defaultValue: Readonly<ITreatmentPlan> = {};
