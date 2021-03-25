import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Nurse from './nurse';
import NurseDetail from './nurse-detail';
import NurseUpdate from './nurse-update';
import NurseDeleteDialog from './nurse-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NurseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NurseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NurseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Nurse} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NurseDeleteDialog} />
  </>
);

export default Routes;
