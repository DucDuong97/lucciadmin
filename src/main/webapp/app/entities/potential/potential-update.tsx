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
import { IBranch } from 'app/shared/model/branch.model';
import { getEntities as getBranches } from 'app/entities/branch/branch.reducer';
import { getEntity, updateEntity, createEntity, reset } from './potential.reducer';
import { IPotential } from 'app/shared/model/potential.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPotentialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PotentialUpdate = (props: IPotentialUpdateProps) => {
  const [serviceId, setServiceId] = useState('0');
  const [branchId, setBranchId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { potentialEntity, pricingCards, branches, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/potential' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPricingCards();
    props.getBranches();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...potentialEntity,
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
          <h2 id="lucciadminApp.potential.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.potential.home.createOrEditLabel">Create or edit a Potential</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : potentialEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="potential-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="potential-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="potential-name">
                  <Translate contentKey="lucciadminApp.potential.name">Name</Translate>
                </Label>
                <AvField id="potential-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="potential-phone">
                  <Translate contentKey="lucciadminApp.potential.phone">Phone</Translate>
                </Label>
                <AvField id="potential-phone" type="string" className="form-control" name="phone" />
              </AvGroup>
              <AvGroup check>
                <Label id="genderLabel">
                  <AvInput id="potential-gender" type="checkbox" className="form-check-input" name="gender" />
                  <Translate contentKey="lucciadminApp.potential.gender">Gender</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="potential-service">
                  <Translate contentKey="lucciadminApp.potential.service">Service</Translate>
                </Label>
                <AvInput id="potential-service" type="select" className="form-control" name="serviceId" required>
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
                <Label for="potential-branch">
                  <Translate contentKey="lucciadminApp.potential.branch">Branch</Translate>
                </Label>
                <AvInput id="potential-branch" type="select" className="form-control" name="branchId" required>
                  {branches
                    ? branches.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.adress}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/potential" replace color="info">
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
  branches: storeState.branch.entities,
  potentialEntity: storeState.potential.entity,
  loading: storeState.potential.loading,
  updating: storeState.potential.updating,
  updateSuccess: storeState.potential.updateSuccess,
});

const mapDispatchToProps = {
  getPricingCards,
  getBranches,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PotentialUpdate);
