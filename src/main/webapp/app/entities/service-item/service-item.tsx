import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './service-item.reducer';

export interface IServiceItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ServiceItem = (props: IServiceItemProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { serviceItemList, match, loading } = props;
  return (
    <div>
      <h2 id="service-item-heading">
        <Translate contentKey="lucciadminApp.serviceItem.home.title">Service Items</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.serviceItem.home.createLabel">Create new Service Item</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {serviceItemList && serviceItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lucciadminApp.serviceItem.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.serviceItem.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.serviceItem.imgUrl">Img Url</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serviceItemList.map((serviceItem, i) => (
                <tr key={`entity-${i}`}>
                  <td>{serviceItem.name}</td>
                  <td>{serviceItem.description}</td>
                  <td>{serviceItem.imgUrl ? serviceItem.imgUrl.imgUrl : ''}</td>

                  {/*item buttons*/}
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${serviceItem.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${serviceItem.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${serviceItem.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.serviceItem.home.notFound">No Service Items found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ serviceItem }: IRootState) => ({
  serviceItemList: serviceItem.entities,
  loading: serviceItem.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceItem);
