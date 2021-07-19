import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IImgUrl } from 'app/shared/model/img-url.model';
import { getEntities as getImgUrls } from 'app/entities/img-url/img-url.reducer';
import { ITreatmentPlan } from 'app/shared/model/treatment-plan.model';
import { getEntities as getTreatmentPlans } from 'app/entities/treatment-plan/treatment-plan.reducer';
import { getEntity, updateEntity, createEntity, reset } from './treatment.reducer';
import { ITreatment } from 'app/shared/model/treatment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITreatmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TreatmentUpdate = (props: ITreatmentUpdateProps) => {
  const [idstreatmentImgUrl, setIdstreatmentImgUrl] = useState([]);
  const [doctorId, setDoctorId] = useState('0');
  const [treatmentPlanId, setTreatmentPlanId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { treatmentEntity, employees, imgUrls, treatmentPlans, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/treatment' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
    props.getImgUrls();
    props.getTreatmentPlans();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...treatmentEntity,
        ...values,
        treatmentImgUrls: mapIdList(values.treatmentImgUrls),
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
          <h2 id="lucciadminApp.treatment.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.treatment.home.createOrEditLabel">Create or edit a Treatment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : treatmentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="treatment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="treatment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descriptionLabel" for="treatment-description">
                  <Translate contentKey="lucciadminApp.treatment.description">Description</Translate>
                </Label>
                <AvField id="treatment-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="treatment-date">
                  <Translate contentKey="lucciadminApp.treatment.date">Date</Translate>
                </Label>
                <AvField
                  id="treatment-date"
                  type="date"
                  className="form-control"
                  name="date"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nextPlanLabel" for="treatment-nextPlan">
                  <Translate contentKey="lucciadminApp.treatment.nextPlan">Next Plan</Translate>
                </Label>
                <AvField id="treatment-nextPlan" type="text" name="nextPlan" />
              </AvGroup>
              <AvGroup>
                <Label id="revisitDateLabel" for="treatment-revisitDate">
                  <Translate contentKey="lucciadminApp.treatment.revisitDate">Revisit Date</Translate>
                </Label>
                <AvField id="treatment-revisitDate" type="date" className="form-control" name="revisitDate" />
              </AvGroup>
              <AvGroup>
                <Label for="treatment-doctor">
                  <Translate contentKey="lucciadminApp.treatment.doctor">Doctor</Translate>
                </Label>
                <AvInput id="treatment-doctor" type="select" className="form-control" name="doctorId" required>
                  {employees
                    ? employees.map(otherEntity => (
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
                <Label for="treatment-treatmentImgUrl">
                  <Translate contentKey="lucciadminApp.treatment.treatmentImgUrl">Treatment Img Url</Translate>
                </Label>
                <AvInput
                  id="treatment-treatmentImgUrl"
                  type="select"
                  multiple
                  className="form-control"
                  name="treatmentImgUrls"
                  value={treatmentEntity.treatmentImgUrls && treatmentEntity.treatmentImgUrls.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {imgUrls
                    ? imgUrls.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="treatment-treatmentPlan">
                  <Translate contentKey="lucciadminApp.treatment.treatmentPlan">Treatment Plan</Translate>
                </Label>
                <AvInput id="treatment-treatmentPlan" type="select" className="form-control" name="treatmentPlanId" required>
                  {treatmentPlans
                    ? treatmentPlans.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/treatment" replace color="info">
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
  employees: storeState.employee.entities,
  imgUrls: storeState.imgUrl.entities,
  treatmentPlans: storeState.treatmentPlan.entities,
  treatmentEntity: storeState.treatment.entity,
  loading: storeState.treatment.loading,
  updating: storeState.treatment.updating,
  updateSuccess: storeState.treatment.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getImgUrls,
  getTreatmentPlans,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentUpdate);
