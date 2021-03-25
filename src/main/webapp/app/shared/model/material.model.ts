export interface IMaterial {
  id?: number;
  name?: string;
  amount?: number;
  unit?: string;
}

export const defaultValue: Readonly<IMaterial> = {};
