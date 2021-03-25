import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Receptionist from './receptionist';
import ReceptionistDetail from './receptionist-detail';
import ReceptionistUpdate from './receptionist-update';
import ReceptionistDeleteDialog from './receptionist-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReceptionistUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReceptionistUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReceptionistDetail} />
      <ErrorBoundaryRoute path={match.url} component={Receptionist} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReceptionistDeleteDialog} />
  </>
);

export default Routes;
