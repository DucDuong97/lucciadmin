import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment.reducer';
import { ITreatment } from 'app/shared/model/treatment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TreatmentDetail = (props: ITreatmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { treatmentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.treatment.detail.title">Treatment</Translate> [<b>{treatmentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <Translate contentKey="lucciadminApp.treatment.record">Record</Translate>
          </dt>
          <dd>{treatmentEntity.record ? treatmentEntity.record.id : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatment.doctor">Doctor</Translate>
          </dt>
          <dd>
            {treatmentEntity.doctors
              ? treatmentEntity.doctors.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {treatmentEntity.doctors && i === treatmentEntity.doctors.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatment.nurse">Nurse</Translate>
          </dt>
          <dd>
            {treatmentEntity.nurses
              ? treatmentEntity.nurses.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {treatmentEntity.nurses && i === treatmentEntity.nurses.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatment.patient">Patient</Translate>
          </dt>
          <dd>{treatmentEntity.patient ? treatmentEntity.patient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/treatment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/treatment/${treatmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ treatment }: IRootState) => ({
  treatmentEntity: treatment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentDetail);
