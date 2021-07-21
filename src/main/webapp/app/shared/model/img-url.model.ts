export interface IImgUrl {
  id?: number;
  name?: string;
  path?: string;
  imageFile?: File | Blob;
}

export const defaultValue: Readonly<IImgUrl> = {};
