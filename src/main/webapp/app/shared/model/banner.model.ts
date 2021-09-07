export interface IBanner {
  id?: number;
  imgUrlName?: string;
  imgUrlId?: number;
  file?: File;
}

export const defaultValue: Readonly<IBanner> = {};
