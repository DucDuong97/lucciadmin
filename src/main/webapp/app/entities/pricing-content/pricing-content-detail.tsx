import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pricing-content.reducer';
import { IPricingContent } from 'app/shared/model/pricing-content.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPricingContentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PricingContentDetail = (props: IPricingContentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pricingContentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.pricingContent.detail.title">PricingContent</Translate> [<b>{pricingContentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="content">
              <Translate contentKey="lucciadminApp.pricingContent.content">Content</Translate>
            </span>
          </dt>
          <dd>{pricingContentEntity.content}</dd>
          <dt>
            <span id="pro">
              <Translate contentKey="lucciadminApp.pricingContent.pro">Pro</Translate>
            </span>
          </dt>
          <dd>{pricingContentEntity.pro ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.pricingContent.pricingCard">Pricing Card</Translate>
          </dt>
          <dd>{pricingContentEntity.pricingCardName ? pricingContentEntity.pricingCardName : ''}</dd>
        </dl>
        <Button tag={Link} to="/pricing-content" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pricing-content/${pricingContentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pricingContent }: IRootState) => ({
  pricingContentEntity: pricingContent.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PricingContentDetail);
