import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SingletonContent from './singleton-content';
import SingletonContentDetail from './singleton-content-detail';
import SingletonContentUpdate from './singleton-content-update';
import SingletonContentDeleteDialog from './singleton-content-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SingletonContentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SingletonContentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SingletonContentDetail} />
      <ErrorBoundaryRoute path={match.url} component={SingletonContent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SingletonContentDeleteDialog} />
  </>
);

export default Routes;
