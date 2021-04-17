import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Patient from './patient';
import PatientDetail from './patient-detail';
import PatientUpdate from './patient-update';
import PatientDeleteDialog from './patient-delete-dialog';
import {Booking} from "app/entities/booking/booking";
import BookingPatient from "app/entities/booking/booking-patient";

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PatientUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PatientUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PatientDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/booking`}
                          // render={(props) => <BookingPatient {...props}/>}
                          component={BookingPatient}
      />
      <ErrorBoundaryRoute path={match.url} component={Patient} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PatientDeleteDialog} />
  </>
);

export default Routes;
