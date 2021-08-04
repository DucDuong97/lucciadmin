import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Booking from './booking';
import BookingDetail from './booking-detail';
import BookingUpdate from './booking-update';
import BookingDeleteDialog from './booking-delete-dialog';
import BookingCancelDialog from "./booking-cancel-dialog";
import BookingCheckDialog from "./booking-check-dialog";

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BookingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BookingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BookingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Booking} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BookingDeleteDialog} />
    <ErrorBoundaryRoute exact path={`${match.url}/:id/check-confirm`} component={BookingCheckDialog} />
    <ErrorBoundaryRoute exact path={`${match.url}/:id/cancel-confirm`} component={BookingCancelDialog} />
  </>
);

export default Routes;
