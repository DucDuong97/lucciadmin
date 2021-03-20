import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceOption from './service-option';
import ServiceOptionDetail from './service-option-detail';
import ServiceOptionUpdate from './service-option-update';
import ServiceOptionDeleteDialog from './service-option-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceOptionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceOptionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceOptionDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceOption} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceOptionDeleteDialog} />
  </>
);

export default Routes;
