import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceOption from './service-option';
import Policy from './policy';
import CustomerReview from './customer-review';
import Achievement from './achievement';
import ServiceItem from './service-item';
import Blog from './blog';
import SingletonContent from './singleton-content';
import Page from './page';
import Person from './person';
import Patient from './patient';
import Doctor from './doctor';
import Nurse from './nurse';
import Receptionist from './receptionist';
import Accountant from './accountant';
import Material from './material';
import Booking from './booking';
import Payment from './payment';
import MedicalRecord from './medical-record';
import Treatment from './treatment';
import TreatmentHistory from './treatment-history';
import GetMaterial from './get-material';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}service-option`} component={ServiceOption} />
      <ErrorBoundaryRoute path={`${match.url}policy`} component={Policy} />
      <ErrorBoundaryRoute path={`${match.url}customer-review`} component={CustomerReview} />
      <ErrorBoundaryRoute path={`${match.url}achievement`} component={Achievement} />
      <ErrorBoundaryRoute path={`${match.url}service-item`} component={ServiceItem} />
      <ErrorBoundaryRoute path={`${match.url}blog`} component={Blog} />
      <ErrorBoundaryRoute path={`${match.url}singleton-content`} component={SingletonContent} />
      <ErrorBoundaryRoute path={`${match.url}page`} component={Page} />
      <ErrorBoundaryRoute path={`${match.url}person`} component={Person} />
      <ErrorBoundaryRoute path={`${match.url}patient`} component={Patient} />
      <ErrorBoundaryRoute path={`${match.url}doctor`} component={Doctor} />
      <ErrorBoundaryRoute path={`${match.url}nurse`} component={Nurse} />
      <ErrorBoundaryRoute path={`${match.url}receptionist`} component={Receptionist} />
      <ErrorBoundaryRoute path={`${match.url}accountant`} component={Accountant} />
      <ErrorBoundaryRoute path={`${match.url}material`} component={Material} />
      <ErrorBoundaryRoute path={`${match.url}booking`} component={Booking} />
      <ErrorBoundaryRoute path={`${match.url}payment`} component={Payment} />
      <ErrorBoundaryRoute path={`${match.url}medical-record`} component={MedicalRecord} />
      <ErrorBoundaryRoute path={`${match.url}treatment`} component={Treatment} />
      <ErrorBoundaryRoute path={`${match.url}treatment-history`} component={TreatmentHistory} />
      <ErrorBoundaryRoute path={`${match.url}get-material`} component={GetMaterial} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
