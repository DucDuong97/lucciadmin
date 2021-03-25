import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './doctor.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDoctorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DoctorDetail = (props: IDoctorDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { doctorEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.doctor.detail.title">Doctor</Translate> [<b>{doctorEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="salary">
              <Translate contentKey="lucciadminApp.doctor.salary">Salary</Translate>
            </span>
          </dt>
          <dd>{doctorEntity.salary}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.doctor.person">Person</Translate>
          </dt>
          <dd>{doctorEntity.person ? doctorEntity.person.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/doctor" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doctor/${doctorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ doctor }: IRootState) => ({
  doctorEntity: doctor.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DoctorDetail);
