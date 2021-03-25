import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMedicalRecord } from 'app/shared/model/medical-record.model';
import { getEntities as getMedicalRecords } from 'app/entities/medical-record/medical-record.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { getEntities as getDoctors } from 'app/entities/doctor/doctor.reducer';
import { INurse } from 'app/shared/model/nurse.model';
import { getEntities as getNurses } from 'app/entities/nurse/nurse.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { getEntity, updateEntity, createEntity, reset } from './treatment.reducer';
import { ITreatment } from 'app/shared/model/treatment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITreatmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TreatmentUpdate = (props: ITreatmentUpdateProps) => {
  const [idsdoctor, setIdsdoctor] = useState([]);
  const [idsnurse, setIdsnurse] = useState([]);
  const [recordId, setRecordId] = useState('0');
  const [patientId, setPatientId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { treatmentEntity, medicalRecords, doctors, nurses, patients, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/treatment');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMedicalRecords();
    props.getDoctors();
    props.getNurses();
    props.getPatients();
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
        doctors: mapIdList(values.doctors),
        nurses: mapIdList(values.nurses),
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
                <Label for="treatment-record">
                  <Translate contentKey="lucciadminApp.treatment.record">Record</Translate>
                </Label>
                <AvInput id="treatment-record" type="select" className="form-control" name="record.id">
                  <option value="" key="0" />
                  {medicalRecords
                    ? medicalRecords.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="treatment-doctor">
                  <Translate contentKey="lucciadminApp.treatment.doctor">Doctor</Translate>
                </Label>
                <AvInput
                  id="treatment-doctor"
                  type="select"
                  multiple
                  className="form-control"
                  name="doctors"
                  value={treatmentEntity.doctors && treatmentEntity.doctors.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {doctors
                    ? doctors.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="treatment-nurse">
                  <Translate contentKey="lucciadminApp.treatment.nurse">Nurse</Translate>
                </Label>
                <AvInput
                  id="treatment-nurse"
                  type="select"
                  multiple
                  className="form-control"
                  name="nurses"
                  value={treatmentEntity.nurses && treatmentEntity.nurses.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {nurses
                    ? nurses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="treatment-patient">
                  <Translate contentKey="lucciadminApp.treatment.patient">Patient</Translate>
                </Label>
                <AvInput id="treatment-patient" type="select" className="form-control" name="patient.id">
                  <option value="" key="0" />
                  {patients
                    ? patients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
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
  medicalRecords: storeState.medicalRecord.entities,
  doctors: storeState.doctor.entities,
  nurses: storeState.nurse.entities,
  patients: storeState.patient.entities,
  treatmentEntity: storeState.treatment.entity,
  loading: storeState.treatment.loading,
  updating: storeState.treatment.updating,
  updateSuccess: storeState.treatment.updateSuccess,
});

const mapDispatchToProps = {
  getMedicalRecords,
  getDoctors,
  getNurses,
  getPatients,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentUpdate);
