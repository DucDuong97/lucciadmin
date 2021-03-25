import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Accountant from './accountant';
import AccountantDetail from './accountant-detail';
import AccountantUpdate from './accountant-update';
import AccountantDeleteDialog from './accountant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AccountantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AccountantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AccountantDetail} />
      <ErrorBoundaryRoute path={match.url} component={Accountant} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AccountantDeleteDialog} />
  </>
);

export default Routes;
