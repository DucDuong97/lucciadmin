import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PricingContent from './pricing-content';
import PricingContentDetail from './pricing-content-detail';
import PricingContentUpdate from './pricing-content-update';
import PricingContentDeleteDialog from './pricing-content-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PricingContentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PricingContentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PricingContentDetail} />
      <ErrorBoundaryRoute path={match.url} component={PricingContent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PricingContentDeleteDialog} />
  </>
);

export default Routes;
