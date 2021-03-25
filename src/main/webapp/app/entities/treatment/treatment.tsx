import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './treatment.reducer';
import { ITreatment } from 'app/shared/model/treatment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Treatment = (props: ITreatmentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { treatmentList, match, loading } = props;
  return (
    <div>
      <h2 id="treatment-heading">
        <Translate contentKey="lucciadminApp.treatment.home.title">Treatments</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.treatment.home.createLabel">Create new Treatment</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {treatmentList && treatmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatment.record">Record</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatment.doctor">Doctor</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatment.nurse">Nurse</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatment.patient">Patient</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {treatmentList.map((treatment, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${treatment.id}`} color="link" size="sm">
                      {treatment.id}
                    </Button>
                  </td>
                  <td>{treatment.record ? <Link to={`medical-record/${treatment.record.id}`}>{treatment.record.id}</Link> : ''}</td>
                  <td>
                    {treatment.doctors
                      ? treatment.doctors.map((val, j) => (
                          <span key={j}>
                            <Link to={`doctor/${val.id}`}>{val.id}</Link>
                            {j === treatment.doctors.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {treatment.nurses
                      ? treatment.nurses.map((val, j) => (
                          <span key={j}>
                            <Link to={`nurse/${val.id}`}>{val.id}</Link>
                            {j === treatment.nurses.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{treatment.patient ? <Link to={`patient/${treatment.patient.id}`}>{treatment.patient.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${treatment.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${treatment.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${treatment.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.treatment.home.notFound">No Treatments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ treatment }: IRootState) => ({
  treatmentList: treatment.entities,
  loading: treatment.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Treatment);
