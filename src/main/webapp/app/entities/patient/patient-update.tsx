import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPerson } from 'app/shared/model/person.model';
import { getEntities as getPeople } from 'app/entities/person/person.reducer';
import { getEntity, updateEntity, createEntity, reset } from './patient.reducer';
import { getEntity as personGetEntity, updateEntity as personUpdateEntity,
  createEntity as personCreateEntity, reset  as personReset} from '../person/person.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPatientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PatientUpdate = (props: IPatientUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { patientEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/patient');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...patientEntity,
        ...values
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
          <h2 id="lucciadminApp.patient.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.patient.home.createOrEditLabel">Create or edit a Patient</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : patientEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="patient-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="patient-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="person-name">
                  <Translate contentKey="lucciadminApp.person.name">Name</Translate>
                </Label>
                <AvField
                  id="person-name"
                  type="text"
                  name="person.name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 60, errorMessage: translate('entity.validation.maxlength', { max: 60 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="person-phone">
                  <Translate contentKey="lucciadminApp.person.phone">Phone</Translate>
                </Label>
                <AvField
                  id="person-phone"
                  type="text"
                  name="person.phone"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 20, errorMessage: translate('entity.validation.maxlength', { max: 20 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="adressLabel" for="person-adress">
                  <Translate contentKey="lucciadminApp.person.adress">Adress</Translate>
                </Label>
                <AvField id="person-adress" type="text" name="person.adress" />
              </AvGroup>
              <AvGroup>
                <Label id="birthLabel" for="person-birth">
                  <Translate contentKey="lucciadminApp.person.birth">Birth</Translate>
                </Label>
                <AvInput
                  id="person-birth"
                  type="datetime-local"
                  className="form-control"
                  name="person.birth"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(patientEntity.person.birth)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="genderLabel" for="person-gender">
                  <Translate contentKey="lucciadminApp.person.gender">Gender</Translate>
                </Label>
                <AvInput
                  id="person-gender"
                  type="select"
                  className="form-control"
                  name="person.gender"
                  value={(!isNew && patientEntity.person.gender) || 'MALE'}
                >
                  <option value="MALE">{translate('lucciadminApp.Gender.MALE')}</option>
                  <option value="FEMALE">{translate('lucciadminApp.Gender.FEMALE')}</option>
                  <option value="AMBIGIOUS">{translate('lucciadminApp.Gender.AMBIGIOUS')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/patient" replace color="info">
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
  // people: storeState.person.entities,
  patientEntity: storeState.patient.entity,
  loading: storeState.patient.loading,
  updating: storeState.patient.updating,
  updateSuccess: storeState.patient.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PatientUpdate);
