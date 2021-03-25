import { IPerson } from 'app/shared/model/person.model';
import { ITreatment } from 'app/shared/model/treatment.model';

export interface INurse {
  id?: number;
  salary?: number;
  person?: IPerson;
  treatments?: ITreatment[];
}

export const defaultValue: Readonly<INurse> = {};
