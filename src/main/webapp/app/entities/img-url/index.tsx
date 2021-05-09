import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ImgUrl from './img-url';
import ImgUrlDetail from './img-url-detail';
import ImgUrlUpdate from './img-url-update';
import ImgUrlDeleteDialog from './img-url-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ImgUrlUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ImgUrlUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ImgUrlDetail} />
      <ErrorBoundaryRoute path={match.url} component={ImgUrl} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ImgUrlDeleteDialog} />
  </>
);

export default Routes;
