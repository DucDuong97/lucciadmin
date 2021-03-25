import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MedicalRecord from './medical-record';
import MedicalRecordDetail from './medical-record-detail';
import MedicalRecordUpdate from './medical-record-update';
import MedicalRecordDeleteDialog from './medical-record-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MedicalRecordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MedicalRecordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MedicalRecordDetail} />
      <ErrorBoundaryRoute path={match.url} component={MedicalRecord} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MedicalRecordDeleteDialog} />
  </>
);

export default Routes;
