import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeUpdate = (props: IEmployeeUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/employee' + props.location.search);
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
    values.birth = convertDateTimeToServer(values.birth);

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
                    maxLength: { value: 60, errorMessage: translate('entity.validation.maxlength', { max: 60 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="employee-phone">
                  <Translate contentKey="lucciadminApp.employee.phone">Phone</Translate>
                </Label>
                <AvField
                  id="employee-phone"
                  type="text"
                  name="phone"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 20, errorMessage: translate('entity.validation.maxlength', { max: 20 }) },
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
                <AvInput
                  id="employee-birth"
                  type="datetime-local"
                  className="form-control"
                  name="birth"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeEntity.birth)}
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
                >
                  <option value="DOCTOR">{translate('lucciadminApp.EmployeeRole.DOCTOR')}</option>
                  <option value="NURSE">{translate('lucciadminApp.EmployeeRole.NURSE')}</option>
                  <option value="MARKETING">{translate('lucciadminApp.EmployeeRole.MARKETING')}</option>
                  <option value="RECEPTIONIST">{translate('lucciadminApp.EmployeeRole.RECEPTIONIST')}</option>
                  <option value="ADMIN">{translate('lucciadminApp.EmployeeRole.ADMIN')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="salaryLabel" for="employee-salary">
                  <Translate contentKey="lucciadminApp.employee.salary">Salary</Translate>
                </Label>
                <AvField id="employee-salary" type="string" className="form-control" name="salary" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/employee" replace color="info">
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
  employeeEntity: storeState.employee.entity,
  loading: storeState.employee.loading,
  updating: storeState.employee.updating,
  updateSuccess: storeState.employee.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeUpdate);
