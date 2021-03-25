import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './material.reducer';
import { IMaterial } from 'app/shared/model/material.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMaterialProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Material = (props: IMaterialProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { materialList, match, loading } = props;
  return (
    <div>
      <h2 id="material-heading">
        <Translate contentKey="lucciadminApp.material.home.title">Materials</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.material.home.createLabel">Create new Material</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {materialList && materialList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.material.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.material.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.material.unit">Unit</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {materialList.map((material, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${material.id}`} color="link" size="sm">
                      {material.id}
                    </Button>
                  </td>
                  <td>{material.name}</td>
                  <td>{material.amount}</td>
                  <td>{material.unit}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${material.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${material.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${material.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.material.home.notFound">No Materials found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ material }: IRootState) => ({
  materialList: material.entities,
  loading: material.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Material);
