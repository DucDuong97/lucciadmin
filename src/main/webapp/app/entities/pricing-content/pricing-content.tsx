import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './pricing-content.reducer';
import { IPricingContent } from 'app/shared/model/pricing-content.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import {faCheck, faTimes} from "@fortawesome/free-solid-svg-icons";

export interface IPricingContentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const PricingContent = (props: IPricingContentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { pricingContentList, match, loading } = props;
  return (
    <div>
      <h2 id="pricing-content-heading">
        <Translate contentKey="lucciadminApp.pricingContent.home.title">Pricing Contents</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.pricingContent.home.createLabel">Create new Pricing Content</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {pricingContentList && pricingContentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.pricingContent.content">Content</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.pricingContent.pro">Pro</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.pricingContent.pricingCard">Pricing Card</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pricingContentList.map((pricingContent, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${pricingContent.id}`} color="link" size="sm">
                      {pricingContent.id}
                    </Button>
                  </td>
                  <td>{pricingContent.content}</td>
                  <td><FontAwesomeIcon icon={pricingContent.pro ? faCheck : faTimes} /></td>
                  <td>
                    {pricingContent.pricingCard ? (
                      <Link to={`pricing-card/${pricingContent.pricingCard.id}`}>{pricingContent.pricingCard.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${pricingContent.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pricingContent.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pricingContent.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.pricingContent.home.notFound">No Pricing Contents found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ pricingContent }: IRootState) => ({
  pricingContentList: pricingContent.entities,
  loading: pricingContent.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PricingContent);
