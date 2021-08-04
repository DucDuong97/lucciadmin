import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { IPricingCard } from 'app/shared/model/pricing-card.model';
import { getEntities as getPricingCards } from 'app/entities/pricing-card/pricing-card.reducer';
import { getEntity, updateEntity, createEntity, reset } from './treatment-plan.reducer';
import { ITreatmentPlan } from 'app/shared/model/treatment-plan.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITreatmentPlanUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TreatmentPlanUpdate = (props: ITreatmentPlanUpdateProps) => {
  const [customerId, setCustomerId] = useState('0');
  const [serviceId, setServiceId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { treatmentPlanEntity, customers, pricingCards, loading, updating } = props;

  const handleClose = () => {
    props.history.goBack();
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
      const params = new URLSearchParams(props.location.search);
      const customerIdMaybe = params.get('customerId');
      if (customerIdMaybe) {
        setCustomerId(customerIdMaybe);
      }
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCustomers();
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
        ...treatmentPlanEntity,
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
          <h2 id="lucciadminApp.treatmentPlan.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.treatmentPlan.home.createOrEditLabel">Create or edit a TreatmentPlan</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : treatmentPlanEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="treatment-plan-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="treatment-plan-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}

              <AvGroup>
                <Label for="treatment-plan-customer">
                  <Translate contentKey="lucciadminApp.treatmentPlan.customer">Customer</Translate>
                </Label>
                <AvInput id="treatment-plan-customer" type="select" className="form-control" name="customerId" value={isNew ? customerId : treatmentPlanEntity.customerId} disabled={!isNew} required>
                  {customers
                    ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {`${otherEntity.name} - ${otherEntity.id}`}
                      </option>
                    ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="treatment-plan-service">
                  <Translate contentKey="lucciadminApp.treatmentPlan.service">Service</Translate>
                </Label>
                <AvInput id="treatment-plan-service" type="select" className="form-control" name="serviceId" disabled={!isNew} required>
                  {pricingCards
                    ? pricingCards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label id="presentComplaintLabel" for="treatment-plan-presentComplaint">
                  <Translate contentKey="lucciadminApp.treatmentPlan.presentComplaint">Present Complaint</Translate>
                </Label>
                <AvField id="treatment-plan-presentComplaint" type="text" name="presentComplaint" />
              </AvGroup>
              <AvGroup>
                <Label id="pastMedicalHistoryLabel" for="treatment-plan-pastMedicalHistory">
                  <Translate contentKey="lucciadminApp.treatmentPlan.pastMedicalHistory">Past Medical History</Translate>
                </Label>
                <AvField id="treatment-plan-pastMedicalHistory" type="text" name="pastMedicalHistory" />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="treatment-plan-note">
                  <Translate contentKey="lucciadminApp.treatmentPlan.note">Note</Translate>
                </Label>
                <AvField id="treatment-plan-note" type="text" name="note" />
              </AvGroup>
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
  customers: storeState.customer.entities,
  pricingCards: storeState.pricingCard.entities,
  treatmentPlanEntity: storeState.treatmentPlan.entity,
  loading: storeState.treatmentPlan.loading,
  updating: storeState.treatmentPlan.updating,
  updateSuccess: storeState.treatmentPlan.updateSuccess,
});

const mapDispatchToProps = {
  getCustomers,
  getPricingCards,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentPlanUpdate);
