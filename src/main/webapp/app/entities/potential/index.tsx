import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Potential from './potential';
import PotentialDetail from './potential-detail';
import PotentialUpdate from './potential-update';
import PotentialDeleteDialog from './potential-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PotentialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PotentialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PotentialDetail} />
      <ErrorBoundaryRoute path={match.url} component={Potential} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PotentialDeleteDialog} />
  </>
);

export default Routes;
