import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceItem from './service-item';
import ServiceItemDetail from './service-item-detail';
import ServiceItemUpdate from './service-item-update';
import ServiceItemDeleteDialog from './service-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceItemDeleteDialog} />
  </>
);

export default Routes;
