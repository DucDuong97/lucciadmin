import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './service-option.reducer';
import { IServiceOption } from 'app/shared/model/service-option.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceOptionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ServiceOption = (props: IServiceOptionProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { serviceOptionList, match, loading } = props;
  return (
    <div>
      <h2 id="service-option-heading">
        <Translate contentKey="lucciadminApp.serviceOption.home.title">Service Options</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.serviceOption.home.createLabel">Create new Service Option</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {serviceOptionList && serviceOptionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.serviceOption.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.serviceOption.benefits">Benefits</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.serviceOption.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.serviceOption.service">Service</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serviceOptionList.map((serviceOption, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${serviceOption.id}`} color="link" size="sm">
                      {serviceOption.id}
                    </Button>
                  </td>
                  <td>{serviceOption.name}</td>
                  <td>{serviceOption.benefits}</td>
                  <td>{serviceOption.price}</td>
                  <td>
                    {serviceOption.service ? <Link to={`service-item/${serviceOption.service.name}`}>{serviceOption.service.name}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${serviceOption.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${serviceOption.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${serviceOption.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.serviceOption.home.notFound">No Service Options found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ serviceOption }: IRootState) => ({
  serviceOptionList: serviceOption.entities,
  loading: serviceOption.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceOption);
