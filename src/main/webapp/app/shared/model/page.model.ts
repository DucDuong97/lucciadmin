import { SubPage } from 'app/shared/model/enumerations/sub-page.model';

export interface IPage {
  id?: number;
  page?: SubPage;
  description?: string;
}

export const defaultValue: Readonly<IPage> = {};
