export interface IProcess {
  id?: number;
  name?: string;
  order?: number;
  serviceItemName?: string;
  serviceItemId?: number;
  iconName?: string;
  iconId?: number;
}

export const defaultValue: Readonly<IProcess> = {};
