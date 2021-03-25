import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './doctor.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDoctorProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Doctor = (props: IDoctorProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { doctorList, match, loading } = props;
  return (
    <div>
      <h2 id="doctor-heading">
        <Translate contentKey="lucciadminApp.doctor.home.title">Doctors</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.doctor.home.createLabel">Create new Doctor</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {doctorList && doctorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.doctor.salary">Salary</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.doctor.person">Person</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {doctorList.map((doctor, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${doctor.id}`} color="link" size="sm">
                      {doctor.id}
                    </Button>
                  </td>
                  <td>{doctor.salary}</td>
                  <td>{doctor.person ? <Link to={`person/${doctor.person.id}`}>{doctor.person.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${doctor.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${doctor.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${doctor.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.doctor.home.notFound">No Doctors found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ doctor }: IRootState) => ({
  doctorList: doctor.entities,
  loading: doctor.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Doctor);
