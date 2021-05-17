import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PricingCard from './pricing-card';
import PricingCardDetail from './pricing-card-detail';
import PricingCardUpdate from './pricing-card-update';
import PricingCardDeleteDialog from './pricing-card-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PricingCardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PricingCardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PricingCardDetail} />
      <ErrorBoundaryRoute path={match.url} component={PricingCard} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PricingCardDeleteDialog} />
  </>
);

export default Routes;
