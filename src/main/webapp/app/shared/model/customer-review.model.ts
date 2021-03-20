export interface ICustomerReview {
  id?: number;
  customerName?: string;
  customerTitle?: string;
  comment?: string;
}

export const defaultValue: Readonly<ICustomerReview> = {};
