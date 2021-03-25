import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GetMaterial from './get-material';
import GetMaterialDetail from './get-material-detail';
import GetMaterialUpdate from './get-material-update';
import GetMaterialDeleteDialog from './get-material-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GetMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GetMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GetMaterialDetail} />
      <ErrorBoundaryRoute path={match.url} component={GetMaterial} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GetMaterialDeleteDialog} />
  </>
);

export default Routes;
