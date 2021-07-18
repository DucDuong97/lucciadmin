import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Consult from './consult';
import ConsultDetail from './consult-detail';
import ConsultUpdate from './consult-update';
import ConsultDeleteDialog from './consult-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConsultUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConsultUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConsultDetail} />
      <ErrorBoundaryRoute path={match.url} component={Consult} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConsultDeleteDialog} />
  </>
);

export default Routes;
