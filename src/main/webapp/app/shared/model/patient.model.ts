import { IPerson } from 'app/shared/model/person.model';
import { IBooking } from 'app/shared/model/booking.model';
import { ITreatment } from 'app/shared/model/treatment.model';

export interface IPatient {
  id?: number;
  person?: IPerson;
  bookings?: IBooking[];
  treatments?: ITreatment[];
}

export const defaultValue: Readonly<IPatient> = {};
