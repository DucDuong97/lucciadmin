import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './customer-review.reducer';
import { ICustomerReview } from 'app/shared/model/customer-review.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICustomerReviewDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CustomerReviewDetail = (props: ICustomerReviewDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { customerReviewEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.customerReview.detail.title">CustomerReview</Translate> [<b>{customerReviewEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="customerName">
              <Translate contentKey="lucciadminApp.customerReview.customerName">Customer Name</Translate>
            </span>
          </dt>
          <dd>{customerReviewEntity.customerName}</dd>
          <dt>
            <span id="customerTitle">
              <Translate contentKey="lucciadminApp.customerReview.customerTitle">Customer Title</Translate>
            </span>
          </dt>
          <dd>{customerReviewEntity.customerTitle}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="lucciadminApp.customerReview.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{customerReviewEntity.comment}</dd>
        </dl>
        <Button tag={Link} to="/customer-review" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer-review/${customerReviewEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ customerReview }: IRootState) => ({
  customerReviewEntity: customerReview.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CustomerReviewDetail);
