import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './patient.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPatientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Patient = (props: IPatientProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { patientList, match, loading } = props;
  return (
    <div>
      <h2 id="patient-heading">
        <Translate contentKey="lucciadminApp.patient.home.title">Patients</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.patient.home.createLabel">Create new Patient</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {patientList && patientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.patient.person">Person</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.patient.bookings">Bookings</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.patient.treatments">Treatments</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {patientList.map((patient, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${patient.id}`} color="link" size="sm">
                      {patient.id}
                    </Button>
                  </td>
                  <td>{patient.person ? <Link to={`person/${patient.person.id}`}>{patient.person.name} - {patient.person.phone}</Link> : ''}</td>

                  <td className="text-left">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${patient.id}/booking`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">Option List</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>

                  <td className="text-left">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/treatment?patient_id=${patient.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">Option List</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>

                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${patient.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${patient.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${patient.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.patient.home.notFound">No Patients found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ patient }: IRootState) => ({
  patientList: patient.entities,
  loading: patient.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Patient);
