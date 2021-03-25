import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TreatmentHistory from './treatment-history';
import TreatmentHistoryDetail from './treatment-history-detail';
import TreatmentHistoryUpdate from './treatment-history-update';
import TreatmentHistoryDeleteDialog from './treatment-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TreatmentHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TreatmentHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TreatmentHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={TreatmentHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TreatmentHistoryDeleteDialog} />
  </>
);

export default Routes;
