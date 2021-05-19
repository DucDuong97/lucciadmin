import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPricingCard } from 'app/shared/model/pricing-card.model';
import { getEntities as getPricingCards } from 'app/entities/pricing-card/pricing-card.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pricing-content.reducer';
import { IPricingContent } from 'app/shared/model/pricing-content.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPricingContentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PricingContentUpdate = (props: IPricingContentUpdateProps) => {
  const [pricingCardId, setPricingCardId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { pricingContentEntity, pricingCards, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pricing-content');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPricingCards();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...pricingContentEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lucciadminApp.pricingContent.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.pricingContent.home.createOrEditLabel">Create or edit a PricingContent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pricingContentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pricing-content-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="pricing-content-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentLabel" for="pricing-content-content">
                  <Translate contentKey="lucciadminApp.pricingContent.content">Content</Translate>
                </Label>
                <AvField
                  id="pricing-content-content"
                  type="text"
                  name="content"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="proLabel">
                  <AvInput id="pricing-content-pro" type="checkbox" className="form-check-input" name="pro" />
                  <Translate contentKey="lucciadminApp.pricingContent.pro">Pro</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="pricing-content-pricingCard">
                  <Translate contentKey="lucciadminApp.pricingContent.pricingCard">Pricing Card</Translate>
                </Label>
                <AvInput id="pricing-content-pricingCard" type="select" className="form-control" name="pricingCard.id">
                  <option value="" key="0" />
                  {pricingCards
                    ? pricingCards.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pricing-content" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  pricingCards: storeState.pricingCard.entities,
  pricingContentEntity: storeState.pricingContent.entity,
  loading: storeState.pricingContent.loading,
  updating: storeState.pricingContent.updating,
  updateSuccess: storeState.pricingContent.updateSuccess,
});

const mapDispatchToProps = {
  getPricingCards,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PricingContentUpdate);
