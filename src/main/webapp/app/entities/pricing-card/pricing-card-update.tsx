import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceItem } from 'app/shared/model/service-item.model';
import { getEntities as getServiceItems } from 'app/entities/service-item/service-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pricing-card.reducer';
import { IPricingCard } from 'app/shared/model/pricing-card.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPricingCardUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PricingCardUpdate = (props: IPricingCardUpdateProps) => {
  const [serviceItemId, setServiceItemId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { pricingCardEntity, serviceItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pricing-card');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...pricingCardEntity,
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
          <h2 id="lucciadminApp.pricingCard.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.pricingCard.home.createOrEditLabel">Create or edit a PricingCard</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pricingCardEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pricing-card-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="pricing-card-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="pricing-card-name">
                  <Translate contentKey="lucciadminApp.pricingCard.name">Name</Translate>
                </Label>
                <AvField
                  id="pricing-card-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="pricing-card-price">
                  <Translate contentKey="lucciadminApp.pricingCard.price">Price</Translate>
                </Label>
                <AvField
                  id="pricing-card-price"
                  type="string"
                  className="form-control"
                  name="price"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="pricing-card-serviceItem">
                  <Translate contentKey="lucciadminApp.pricingCard.serviceItem">Service Item</Translate>
                </Label>
                <AvInput id="pricing-card-serviceItem" type="select" className="form-control" name="serviceItem.id">
                  <option value="" key="0" />
                  {serviceItems
                    ? serviceItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pricing-card" replace color="info">
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
  serviceItems: storeState.serviceItem.entities,
  pricingCardEntity: storeState.pricingCard.entity,
  loading: storeState.pricingCard.loading,
  updating: storeState.pricingCard.updating,
  updateSuccess: storeState.pricingCard.updateSuccess,
});

const mapDispatchToProps = {
  getServiceItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PricingCardUpdate);
