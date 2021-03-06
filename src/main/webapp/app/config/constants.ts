const config = {
  VERSION: process.env.VERSION,
};

export default config;

export const SERVER_API_URL = process.env.SERVER_API_URL;
export const IMAGE_FILE_SYSTEM_URL = `https://lucci${
  process.env.NODE_ENV === 'development' ? '-dev' : ''
}.s3-ap-southeast-1.amazonaws.com/`;

export const AUTHORITIES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER',
  RECEPTIONIST: 'ROLE_RECEPTIONIST',
  DOCTOR: 'ROLE_DOCTOR',
  NURSE: 'ROLE_NURSE',
  MARKETING: 'ROLE_MARKETING',
  MANAGER: 'ROLE_MANAGER',
  OPERATIONS_DIRECTOR: 'ROLE_OPERATIONS_DIRECTOR',
  BRANCH_BOSS_DOCTOR: 'ROLE_BRANCH_BOSS_DOCTOR',
  CONSULTANT: 'ROLE_CONSULTANT',
  B2C_MARKETER: 'ROLE_B2C_MARKETER',
};

export const messages = {
  DATA_ERROR_ALERT: 'Internal Error',
};

export const APP_DATE_FORMAT = 'DD/MM/YY HH:mm';
export const APP_TIME_FORMAT = 'HH:mm';
export const APP_TIMESTAMP_FORMAT = 'DD/MM/YY HH:mm:ss';
export const APP_LOCAL_DATE_FORMAT = 'DD/MM/YYYY';
export const APP_LOCAL_DATETIME_FORMAT = 'YYYY-MM-DDTHH:mm';
export const APP_LOCAL_DATETIME_FORMAT_Z = 'YYYY-MM-DDTHH:mm Z';
export const APP_WHOLE_NUMBER_FORMAT = '0,0';
export const APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT = '0,0.[00]';
