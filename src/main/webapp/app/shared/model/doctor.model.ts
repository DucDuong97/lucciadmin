import { IPerson } from 'app/shared/model/person.model';
import { IBooking } from 'app/shared/model/booking.model';
import { ITreatment } from 'app/shared/model/treatment.model';

export interface IDoctor {
  id?: number;
  salary?: number;
  person?: IPerson;
  bookings?: IBooking;
  treatments?: ITreatment[];
}

export const defaultValue: Readonly<IDoctor> = {};
