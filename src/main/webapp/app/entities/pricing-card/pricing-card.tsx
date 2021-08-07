import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './pricing-card.reducer';
import { IPricingCard } from 'app/shared/model/pricing-card.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPricingCardProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const PricingCard = (props: IPricingCardProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { pricingCardList, match, loading } = props;
  return (
    <div>
      <h2 id="pricing-card-heading">
        <Translate contentKey="lucciadminApp.pricingCard.home.title">Pricing Cards</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.pricingCard.home.createLabel">Create new Pricing Card</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {pricingCardList && pricingCardList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.pricingCard.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.pricingCard.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.pricingCard.serviceItem">Service Item</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pricingCardList.map((pricingCard, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${pricingCard.id}`} color="link" size="sm">
                      {pricingCard.id}
                    </Button>
                  </td>
                  <td>{pricingCard.name}</td>
                  <td>{pricingCard.price}</td>
                  <td>
                    {pricingCard.serviceItemName ? (
                      <Link to={`service-item/${pricingCard.serviceItemId}`}>{pricingCard.serviceItemName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${pricingCard.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pricingCard.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pricingCard.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.pricingCard.home.notFound">No Pricing Cards found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ pricingCard }: IRootState) => ({
  pricingCardList: pricingCard.entities,
  loading: pricingCard.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PricingCard);
