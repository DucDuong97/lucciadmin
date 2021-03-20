import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CustomerReview from './customer-review';
import CustomerReviewDetail from './customer-review-detail';
import CustomerReviewUpdate from './customer-review-update';
import CustomerReviewDeleteDialog from './customer-review-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CustomerReviewUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CustomerReviewUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CustomerReviewDetail} />
      <ErrorBoundaryRoute path={match.url} component={CustomerReview} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CustomerReviewDeleteDialog} />
  </>
);

export default Routes;
