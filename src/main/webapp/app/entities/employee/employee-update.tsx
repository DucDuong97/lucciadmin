import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBranch } from 'app/shared/model/branch.model';
import { getEntities as getBranches } from 'app/entities/branch/branch.reducer';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeUpdate = (props: IEmployeeUpdateProps) => {
  const [workAtId, setWorkAtId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeEntity, branches, loading, updating } = props;

  const handleClose = () => {
    props.history.goBack();
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...employeeEntity,
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
          <h2 id="lucciadminApp.employee.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employee-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="employee-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="employee-name">
                  <Translate contentKey="lucciadminApp.employee.name">Name</Translate>
                </Label>
                <AvField
                  id="employee-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 30, errorMessage: translate('entity.validation.maxlength', { max: 30 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="employee-phone">
                  <Translate contentKey="lucciadminApp.employee.phone">Phone</Translate>
                </Label>
                <AvField
                  id="employee-phone"
                  type="string"
                  className="form-control"
                  name="phone"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="employee-address">
                  <Translate contentKey="lucciadminApp.employee.address">Address</Translate>
                </Label>
                <AvField id="employee-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="birthLabel" for="employee-birth">
                  <Translate contentKey="lucciadminApp.employee.birth">Birth</Translate>
                </Label>
                <AvField
                  id="employee-birth"
                  type="date"
                  className="form-control"
                  name="birth"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="genderLabel" for="employee-gender">
                  <Translate contentKey="lucciadminApp.employee.gender">Gender</Translate>
                </Label>
                <AvInput
                  id="employee-gender"
                  type="select"
                  className="form-control"
                  name="gender"
                  value={(!isNew && employeeEntity.gender) || 'MALE'}
                >
                  <option value="MALE">{translate('lucciadminApp.Gender.MALE')}</option>
                  <option value="FEMALE">{translate('lucciadminApp.Gender.FEMALE')}</option>
                  <option value="AMBIGUOUS">{translate('lucciadminApp.Gender.AMBIGUOUS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="roleLabel" for="employee-role">
                  <Translate contentKey="lucciadminApp.employee.role">Role</Translate>
                </Label>
                <AvInput
                  id="employee-role"
                  type="select"
                  className="form-control"
                  name="role"
                  value={(!isNew && employeeEntity.role) || 'DOCTOR'}
                  disabled={!isNew}
                >
                  <option value="DOCTOR">{translate('lucciadminApp.EmployeeRole.DOCTOR')}</option>
                  <option value="NURSE">{translate('lucciadminApp.EmployeeRole.NURSE')}</option>
                  <option value="MARKETING">{translate('lucciadminApp.EmployeeRole.MARKETING')}</option>
                  <option value="RECEPTIONIST">{translate('lucciadminApp.EmployeeRole.RECEPTIONIST')}</option>
                  <option value="ADMIN">{translate('lucciadminApp.EmployeeRole.ADMIN')}</option>
                  <option value="OPERATIONS_DIRECTOR">{translate('lucciadminApp.EmployeeRole.OPERATIONS_DIRECTOR')}</option>
                  <option value="BRANCH_BOSS_DOCTOR">{translate('lucciadminApp.EmployeeRole.BRANCH_BOSS_DOCTOR')}</option>
                  <option value="CONSULTANT">{translate('lucciadminApp.EmployeeRole.CONSULTANT')}</option>
                  <option value="B2C_MARKETER">{translate('lucciadminApp.EmployeeRole.B2C_MARKETER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employee-workAt">
                  <Translate contentKey="lucciadminApp.employee.workAt">Work At</Translate>
                </Label>
                <AvInput id="employee-workAt" type="select" className="form-control" name="workAtId">
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
  branches: storeState.branch.entities,
  employeeEntity: storeState.employee.entity,
  loading: storeState.employee.loading,
  updating: storeState.employee.updating,
  updateSuccess: storeState.employee.updateSuccess,
});

const mapDispatchToProps = {
  getBranches,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeUpdate);
