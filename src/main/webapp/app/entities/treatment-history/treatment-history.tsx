import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './treatment-history.reducer';
import { ITreatmentHistory } from 'app/shared/model/treatment-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentHistoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const TreatmentHistory = (props: ITreatmentHistoryProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { treatmentHistoryList, match, loading } = props;
  return (
    <div>
      <h2 id="treatment-history-heading">
        <Translate contentKey="lucciadminApp.treatmentHistory.home.title">Treatment Histories</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.treatmentHistory.home.createLabel">Create new Treatment History</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {treatmentHistoryList && treatmentHistoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatmentHistory.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatmentHistory.treatment">Treatment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {treatmentHistoryList.map((treatmentHistory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${treatmentHistory.id}`} color="link" size="sm">
                      {treatmentHistory.id}
                    </Button>
                  </td>
                  <td>
                    {treatmentHistory.date ? <TextFormat type="date" value={treatmentHistory.date} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {treatmentHistory.treatment ? (
                      <Link to={`treatment/${treatmentHistory.treatment.id}`}>{treatmentHistory.treatment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${treatmentHistory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${treatmentHistory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${treatmentHistory.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="lucciadminApp.treatmentHistory.home.notFound">No Treatment Histories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ treatmentHistory }: IRootState) => ({
  treatmentHistoryList: treatmentHistory.entities,
  loading: treatmentHistory.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentHistory);
