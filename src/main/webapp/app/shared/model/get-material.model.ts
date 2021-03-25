import { Moment } from 'moment';
import { IMaterial } from 'app/shared/model/material.model';
import { IReceptionist } from 'app/shared/model/receptionist.model';

export interface IGetMaterial {
  id?: number;
  date?: string;
  material?: IMaterial;
  receptionist?: IReceptionist;
}

export const defaultValue: Readonly<IGetMaterial> = {};
