import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeDetail = (props: IEmployeeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.employee.detail.title">Employee</Translate> [<b>{employeeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="lucciadminApp.employee.name">Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="lucciadminApp.employee.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.phone}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="lucciadminApp.employee.address">Address</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.address}</dd>
          <dt>
            <span id="birth">
              <Translate contentKey="lucciadminApp.employee.birth">Birth</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.birth ? <TextFormat value={employeeEntity.birth} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="lucciadminApp.employee.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.gender}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="lucciadminApp.employee.role">Role</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.role}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.employee.workAt">Work At</Translate>
          </dt>
          <dd>{employeeEntity.workAtId ? employeeEntity.workAtId : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeEntity: employee.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDetail);
