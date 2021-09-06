export interface IPotential {
  id?: number;
  name?: string;
  phone?: number;
  gender?: boolean;
  serviceName?: string;
  serviceId?: number;
  branchAdress?: string;
  branchId?: number;
}

export const defaultValue: Readonly<IPotential> = {
  gender: false,
};
