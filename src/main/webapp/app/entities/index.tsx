import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Policy from './policy';
import CustomerReview from './customer-review';
import Achievement from './achievement';
import ServiceItem from './service-item';
import Blog from './blog';
import SingletonContent from './singleton-content';
import Customer from './customer';
import Employee from './employee';
import Booking from './booking';
import Payment from './payment';
import Consult from './consult';
import Treatment from './treatment';
import TreatmentPlan from './treatment-plan';
import ImgUrl from './img-url';
import Video from './video';
import Branch from './branch';
import PricingCard from './pricing-card';
import PricingContent from './pricing-content';
import Process from './process';
import Potential from './potential';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}policy`} component={Policy} />
      <ErrorBoundaryRoute path={`${match.url}customer-review`} component={CustomerReview} />
      <ErrorBoundaryRoute path={`${match.url}achievement`} component={Achievement} />
      <ErrorBoundaryRoute path={`${match.url}service-item`} component={ServiceItem} />
      <ErrorBoundaryRoute path={`${match.url}blog`} component={Blog} />
      <ErrorBoundaryRoute path={`${match.url}singleton-content`} component={SingletonContent} />
      <ErrorBoundaryRoute path={`${match.url}customer`} component={Customer} />
      <ErrorBoundaryRoute path={`${match.url}employee`} component={Employee} />
      <ErrorBoundaryRoute path={`${match.url}booking`} component={Booking} />
      <ErrorBoundaryRoute path={`${match.url}payment`} component={Payment} />
      <ErrorBoundaryRoute path={`${match.url}consult`} component={Consult} />
      <ErrorBoundaryRoute path={`${match.url}treatment`} component={Treatment} />
      <ErrorBoundaryRoute path={`${match.url}treatment-plan`} component={TreatmentPlan} />
      <ErrorBoundaryRoute path={`${match.url}img-url`} component={ImgUrl} />
      <ErrorBoundaryRoute path={`${match.url}video`} component={Video} />
      <ErrorBoundaryRoute path={`${match.url}branch`} component={Branch} />
      <ErrorBoundaryRoute path={`${match.url}pricing-card`} component={PricingCard} />
      <ErrorBoundaryRoute path={`${match.url}pricing-content`} component={PricingContent} />
      <ErrorBoundaryRoute path={`${match.url}process`} component={Process} />
      <ErrorBoundaryRoute path={`${match.url}potential`} component={Potential} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
