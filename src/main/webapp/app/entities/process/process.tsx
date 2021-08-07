import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './process.reducer';
import { IProcess } from 'app/shared/model/process.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcessProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Process = (props: IProcessProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { processList, match, loading } = props;
  return (
    <div>
      <h2 id="process-heading">
        <Translate contentKey="lucciadminApp.process.home.title">Processes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.process.home.createLabel">Create new Process</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {processList && processList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.process.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.process.order">Order</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.process.serviceItem">Service Item</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.process.icon">Icon</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {processList.map((process, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${process.id}`} color="link" size="sm">
                      {process.id}
                    </Button>
                  </td>
                  <td>{process.name}</td>
                  <td>{process.order}</td>
                  <td>
                    {process.serviceItemName ? <Link to={`service-item/${process.serviceItemId}`}>{process.serviceItemName}</Link> : ''}
                  </td>
                  <td>{process.iconName ? <Link to={`img-url/${process.iconId}`}>{process.iconName}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${process.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${process.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${process.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.process.home.notFound">No Processes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ process }: IRootState) => ({
  processList: process.entities,
  loading: process.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Process);
