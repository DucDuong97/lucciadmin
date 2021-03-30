import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './customer-review.reducer';
import { ICustomerReview } from 'app/shared/model/customer-review.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICustomerReviewProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CustomerReview = (props: ICustomerReviewProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { customerReviewList, match, loading } = props;
  return (
    <div>
      <h2 id="customer-review-heading">
        <Translate contentKey="lucciadminApp.customerReview.home.title">Customer Reviews</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.customerReview.home.createLabel">Create new Customer Review</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {customerReviewList && customerReviewList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.customerReview.customerName">Customer Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.customerReview.customerTitle">Customer Title</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.customerReview.comment">Comment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {customerReviewList.map((customerReview, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${customerReview.id}`} color="link" size="sm">
                      {customerReview.id}
                    </Button>
                  </td>
                  <td>{customerReview.customerName}</td>
                  <td>{customerReview.customerTitle}</td>
                  <td>{customerReview.comment}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${customerReview.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${customerReview.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${customerReview.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.customerReview.home.notFound">No Customer Reviews found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ customerReview }: IRootState) => ({
  customerReviewList: customerReview.entities,
  loading: customerReview.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CustomerReview);