import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDoctor } from 'app/shared/model/doctor.model';
import { getEntities as getDoctors } from 'app/entities/doctor/doctor.reducer';
import { INurse } from 'app/shared/model/nurse.model';
import { getEntities as getNurses } from 'app/entities/nurse/nurse.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IReceptionist } from 'app/shared/model/receptionist.model';
import { getEntities as getReceptionists } from 'app/entities/receptionist/receptionist.reducer';
import { IAccountant } from 'app/shared/model/accountant.model';
import { getEntities as getAccountants } from 'app/entities/accountant/accountant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './person.reducer';
import { IPerson } from 'app/shared/model/person.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPersonUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PersonUpdate = (props: IPersonUpdateProps) => {
  const [doctorId, setDoctorId] = useState('0');
  const [nurseId, setNurseId] = useState('0');
  const [patientId, setPatientId] = useState('0');
  const [receptionistId, setReceptionistId] = useState('0');
  const [accountantId, setAccountantId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { personEntity, doctors, nurses, patients, receptionists, accountants, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/person');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDoctors();
    props.getNurses();
    props.getPatients();
    props.getReceptionists();
    props.getAccountants();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.birth = convertDateTimeToServer(values.birth);

    if (errors.length === 0) {
      const entity = {
        ...personEntity,
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
          <h2 id="lucciadminApp.person.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.person.home.createOrEditLabel">Create or edit a Person</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : personEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="person-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="person-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="person-name">
                  <Translate contentKey="lucciadminApp.person.name">Name</Translate>
                </Label>
                <AvField
                  id="person-name"
                  type="text"
                  name="name"
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
                  name="phone"
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
                <AvField id="person-adress" type="text" name="adress" />
              </AvGroup>
              <AvGroup>
                <Label id="birthLabel" for="person-birth">
                  <Translate contentKey="lucciadminApp.person.birth">Birth</Translate>
                </Label>
                <AvInput
                  id="person-birth"
                  type="datetime-local"
                  className="form-control"
                  name="birth"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.personEntity.birth)}
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
                  name="gender"
                  value={(!isNew && personEntity.gender) || 'MALE'}
                >
                  <option value="MALE">{translate('lucciadminApp.Gender.MALE')}</option>
                  <option value="FEMALE">{translate('lucciadminApp.Gender.FEMALE')}</option>
                  <option value="AMBIGIOUS">{translate('lucciadminApp.Gender.AMBIGIOUS')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/person" replace color="info">
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
  doctors: storeState.doctor.entities,
  nurses: storeState.nurse.entities,
  patients: storeState.patient.entities,
  receptionists: storeState.receptionist.entities,
  accountants: storeState.accountant.entities,
  personEntity: storeState.person.entity,
  loading: storeState.person.loading,
  updating: storeState.person.updating,
  updateSuccess: storeState.person.updateSuccess,
});

const mapDispatchToProps = {
  getDoctors,
  getNurses,
  getPatients,
  getReceptionists,
  getAccountants,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PersonUpdate);
