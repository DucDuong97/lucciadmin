import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import serviceOption, {
  ServiceOptionState
} from 'app/entities/service-option/service-option.reducer';
// prettier-ignore
import policy, {
  PolicyState
} from 'app/entities/policy/policy.reducer';
// prettier-ignore
import customerReview, {
  CustomerReviewState
} from 'app/entities/customer-review/customer-review.reducer';
// prettier-ignore
import achievement, {
  AchievementState
} from 'app/entities/achievement/achievement.reducer';
// prettier-ignore
import serviceItem, {
  ServiceItemState
} from 'app/entities/service-item/service-item.reducer';
// prettier-ignore
import blog, {
  BlogState
} from 'app/entities/blog/blog.reducer';
// prettier-ignore
import singletonContent, {
  SingletonContentState
} from 'app/entities/singleton-content/singleton-content.reducer';
// prettier-ignore
import page, {
  PageState
} from 'app/entities/page/page.reducer';
// prettier-ignore
import person, {
  PersonState
} from 'app/entities/person/person.reducer';
// prettier-ignore
import patient, {
  PatientState
} from 'app/entities/patient/patient.reducer';
// prettier-ignore
import doctor, {
  DoctorState
} from 'app/entities/doctor/doctor.reducer';
// prettier-ignore
import nurse, {
  NurseState
} from 'app/entities/nurse/nurse.reducer';
// prettier-ignore
import receptionist, {
  ReceptionistState
} from 'app/entities/receptionist/receptionist.reducer';
// prettier-ignore
import accountant, {
  AccountantState
} from 'app/entities/accountant/accountant.reducer';
// prettier-ignore
import material, {
  MaterialState
} from 'app/entities/material/material.reducer';
// prettier-ignore
import booking, {
  BookingState
} from 'app/entities/booking/booking.reducer';
// prettier-ignore
import payment, {
  PaymentState
} from 'app/entities/payment/payment.reducer';
// prettier-ignore
import medicalRecord, {
  MedicalRecordState
} from 'app/entities/medical-record/medical-record.reducer';
// prettier-ignore
import treatment, {
  TreatmentState
} from 'app/entities/treatment/treatment.reducer';
// prettier-ignore
import treatmentHistory, {
  TreatmentHistoryState
} from 'app/entities/treatment-history/treatment-history.reducer';
// prettier-ignore
import getMaterial, {
  GetMaterialState
} from 'app/entities/get-material/get-material.reducer';
// prettier-ignore
import process, {
  ProcessState
} from 'app/entities/process/process.reducer';
// prettier-ignore
import video, {
  VideoState
} from 'app/entities/video/video.reducer';
// prettier-ignore
import imgUrl, {
  ImgUrlState
} from 'app/entities/img-url/img-url.reducer';
// prettier-ignore
import pricingCard, {
  PricingCardState
} from 'app/entities/pricing-card/pricing-card.reducer';
// prettier-ignore
import pricingContent, {
  PricingContentState
} from 'app/entities/pricing-content/pricing-content.reducer';
// prettier-ignore
import branch, {
  BranchState
} from 'app/entities/branch/branch.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly serviceOption: ServiceOptionState;
  readonly policy: PolicyState;
  readonly customerReview: CustomerReviewState;
  readonly achievement: AchievementState;
  readonly serviceItem: ServiceItemState;
  readonly blog: BlogState;
  readonly singletonContent: SingletonContentState;
  readonly page: PageState;
  readonly person: PersonState;
  readonly patient: PatientState;
  readonly doctor: DoctorState;
  readonly nurse: NurseState;
  readonly receptionist: ReceptionistState;
  readonly accountant: AccountantState;
  readonly material: MaterialState;
  readonly booking: BookingState;
  readonly payment: PaymentState;
  readonly medicalRecord: MedicalRecordState;
  readonly treatment: TreatmentState;
  readonly treatmentHistory: TreatmentHistoryState;
  readonly getMaterial: GetMaterialState;
  readonly process: ProcessState;
  readonly video: VideoState;
  readonly imgUrl: ImgUrlState;
  readonly pricingCard: PricingCardState;
  readonly pricingContent: PricingContentState;
  readonly branch: BranchState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  serviceOption,
  policy,
  customerReview,
  achievement,
  serviceItem,
  blog,
  singletonContent,
  page,
  person,
  patient,
  doctor,
  nurse,
  receptionist,
  accountant,
  material,
  booking,
  payment,
  medicalRecord,
  treatment,
  treatmentHistory,
  getMaterial,
  process,
  video,
  imgUrl,
  pricingCard,
  pricingContent,
  branch,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
