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
import customer, {
  CustomerState
} from 'app/entities/customer/customer.reducer';
// prettier-ignore
import employee, {
  EmployeeState
} from 'app/entities/employee/employee.reducer';
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
  readonly customer: CustomerState;
  readonly employee: EmployeeState;
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
  customer,
  employee,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
