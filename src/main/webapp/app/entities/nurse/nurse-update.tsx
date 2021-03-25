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
import { ITreatment } from 'app/shared/model/treatment.model';
import { getEntities as getTreatments } from 'app/entities/treatment/treatment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './nurse.reducer';
import { INurse } from 'app/shared/model/nurse.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INurseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NurseUpdate = (props: INurseUpdateProps) => {
  const [personId, setPersonId] = useState('0');
  const [treatmentsId, setTreatmentsId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { nurseEntity, people, treatments, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/nurse');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPeople();
    props.getTreatments();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...nurseEntity,
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
          <h2 id="lucciadminApp.nurse.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.nurse.home.createOrEditLabel">Create or edit a Nurse</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : nurseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="nurse-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="nurse-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="salaryLabel" for="nurse-salary">
                  <Translate contentKey="lucciadminApp.nurse.salary">Salary</Translate>
                </Label>
                <AvField id="nurse-salary" type="string" className="form-control" name="salary" />
              </AvGroup>
              <AvGroup>
                <Label for="nurse-person">
                  <Translate contentKey="lucciadminApp.nurse.person">Person</Translate>
                </Label>
                <AvInput id="nurse-person" type="select" className="form-control" name="person.id">
                  <option value="" key="0" />
                  {people
                    ? people.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/nurse" replace color="info">
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
  people: storeState.person.entities,
  treatments: storeState.treatment.entities,
  nurseEntity: storeState.nurse.entity,
  loading: storeState.nurse.loading,
  updating: storeState.nurse.updating,
  updateSuccess: storeState.nurse.updateSuccess,
});

const mapDispatchToProps = {
  getPeople,
  getTreatments,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NurseUpdate);
