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
import Customer from './customer';
import Employee from './employee';
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
      <ErrorBoundaryRoute path={`${match.url}customer`} component={Customer} />
      <ErrorBoundaryRoute path={`${match.url}employee`} component={Employee} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
