import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pricing-card.reducer';
import { IPricingCard } from 'app/shared/model/pricing-card.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPricingCardDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PricingCardDetail = (props: IPricingCardDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pricingCardEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.pricingCard.detail.title">PricingCard</Translate> [<b>{pricingCardEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="lucciadminApp.pricingCard.name">Name</Translate>
            </span>
          </dt>
          <dd>{pricingCardEntity.name}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="lucciadminApp.pricingCard.price">Price</Translate>
            </span>
          </dt>
          <dd>{pricingCardEntity.price}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.pricingCard.serviceItem">Service Item</Translate>
          </dt>
          <dd>{pricingCardEntity.serviceItemName ? pricingCardEntity.serviceItemName : ''}</dd>
        </dl>
        <Button tag={Link} to="/pricing-card" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pricing-card/${pricingCardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pricingCard }: IRootState) => ({
  pricingCardEntity: pricingCard.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PricingCardDetail);
