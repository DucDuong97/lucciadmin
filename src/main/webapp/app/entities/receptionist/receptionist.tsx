import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './receptionist.reducer';
import { IReceptionist } from 'app/shared/model/receptionist.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IReceptionistProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Receptionist = (props: IReceptionistProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { receptionistList, match, loading } = props;
  return (
    <div>
      <h2 id="receptionist-heading">
        <Translate contentKey="lucciadminApp.receptionist.home.title">Receptionists</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.receptionist.home.createLabel">Create new Receptionist</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {receptionistList && receptionistList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.receptionist.salary">Salary</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.receptionist.person">Person</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {receptionistList.map((receptionist, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${receptionist.id}`} color="link" size="sm">
                      {receptionist.id}
                    </Button>
                  </td>
                  <td>{receptionist.salary}</td>
                  <td>{receptionist.person ? <Link to={`person/${receptionist.person.id}`}>{receptionist.person.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${receptionist.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${receptionist.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${receptionist.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.receptionist.home.notFound">No Receptionists found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ receptionist }: IRootState) => ({
  receptionistList: receptionist.entities,
  loading: receptionist.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Receptionist);
