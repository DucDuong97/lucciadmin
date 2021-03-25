import { IMedicalRecord } from 'app/shared/model/medical-record.model';
import { ITreatmentHistory } from 'app/shared/model/treatment-history.model';
import { IDoctor } from 'app/shared/model/doctor.model';
import { INurse } from 'app/shared/model/nurse.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface ITreatment {
  id?: number;
  record?: IMedicalRecord;
  histories?: ITreatmentHistory[];
  doctors?: IDoctor[];
  nurses?: INurse[];
  patient?: IPatient;
}

export const defaultValue: Readonly<ITreatment> = {};
