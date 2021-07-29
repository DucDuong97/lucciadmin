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
import { IBranch } from 'app/shared/model/branch.model';
import { getEntities as getBranches } from 'app/entities/branch/branch.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IPricingCard } from 'app/shared/model/pricing-card.model';
import { getEntities as getPricingCards } from 'app/entities/pricing-card/pricing-card.reducer';
import { getEntity, updateEntity, createEntity, reset } from './consult.reducer';
import { IConsult } from 'app/shared/model/consult.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConsultUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsultUpdate = (props: IConsultUpdateProps) => {
  const [idsservice, setIdsservice] = useState([]);
  const [customerId, setCustomerId] = useState('0');
  const [branchId, setBranchId] = useState('0');
  const [consultingDoctorId, setConsultingDoctorId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { consultEntity, customers, branches, employees, pricingCards, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/consult' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
    if (props.isConsultant) {
      props.getCustomers();
      props.getBranches();
      props.getEmployees();
    }
    props.getPricingCards();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.time = convertDateTimeToServer(values.time);

    if (errors.length === 0) {
      const entity = {
        ...consultEntity,
        ...values,
        services: mapIdList(values.services),
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
          <h2 id="lucciadminApp.consult.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.consult.home.createOrEditLabel">Create or edit a Consult</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : consultEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="consult-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="consult-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="timeLabel" for="consult-time">
                  <Translate contentKey="lucciadminApp.consult.time">Time</Translate>
                </Label>
                <AvInput
                  id="consult-time"
                  type="datetime-local"
                  className="form-control"
                  name="time"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.consultEntity.time)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                  disabled={!props.isConsultant}
                />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="consult-note">
                  <Translate contentKey="lucciadminApp.consult.note">Note</Translate>
                </Label>
                <AvField id="consult-note" type="textarea" name="note" />
              </AvGroup>
              {props.isConsultant &&
                <>
                <AvGroup>
                  <Label for="consult-customer">
                    <Translate contentKey="lucciadminApp.consult.customer">Customer</Translate>
                  </Label>
                  <AvInput id="consult-customer" type="select" className="form-control" name="customerId" required>
                    {customers
                      ? customers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            `{otherEntity.name} - {otherEntity.id}`
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvFeedback>
                    <Translate contentKey="entity.validation.required">This field is required.</Translate>
                  </AvFeedback>
                </AvGroup>
                <AvGroup>
                <Label for="consult-branch">
                <Translate contentKey="lucciadminApp.consult.branch">Branch</Translate>
                </Label>
                <AvInput id="consult-branch" type="select" className="form-control" name="branchId">
                <option value="" key="0" />
                {branches
                  ? branches.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.adress}
                    </option>
                  ))
                : null}
                </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="consult-consultingDoctor">
                    <Translate contentKey="lucciadminApp.consult.consultingDoctor">Consulting Doctor</Translate>
                  </Label>
                  <AvInput id="consult-consultingDoctor" type="select" className="form-control" name="consultingDoctorId">
                    <option value="" key="0" />
                    {employees
                      ? employees.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            `{otherEntity.name} - {otherEntity.id}`
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                </>
              }
              <AvGroup>
                <Label for="consult-service">
                  <Translate contentKey="lucciadminApp.consult.service">Service</Translate>
                </Label>
                <AvInput
                  id="consult-service"
                  type="select"
                  multiple
                  className="form-control"
                  name="services"
                  value={consultEntity.services && consultEntity.services.map(e => e.id)}
                  disabled={!props.isDoctor}
                >
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
              <Button tag={Link} id="cancel-save" to="/consult" replace color="info">
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
  customers: storeState.customer.entities,
  branches: storeState.branch.entities,
  employees: storeState.employee.entities,
  pricingCards: storeState.pricingCard.entities,
  consultEntity: storeState.consult.entity,
  loading: storeState.consult.loading,
  updating: storeState.consult.updating,
  updateSuccess: storeState.consult.updateSuccess,

  isConsultant: storeState.authentication.isConsultant,
  isDoctor: storeState.authentication.isDoctor,
});

const mapDispatchToProps = {
  getCustomers,
  getBranches,
  getEmployees,
  getPricingCards,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultUpdate);
