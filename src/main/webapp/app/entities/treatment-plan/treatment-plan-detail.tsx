import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment-plan.reducer';
import { ITreatmentPlan } from 'app/shared/model/treatment-plan.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TreatmentPlanDetail = (props: ITreatmentPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { treatmentPlanEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.treatmentPlan.detail.title">TreatmentPlan</Translate> [<b>{treatmentPlanEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="presentComplaint">
              <Translate contentKey="lucciadminApp.treatmentPlan.presentComplaint">Present Complaint</Translate>
            </span>
          </dt>
          <dd>{treatmentPlanEntity.presentComplaint}</dd>
          <dt>
            <span id="pastMedicalHistory">
              <Translate contentKey="lucciadminApp.treatmentPlan.pastMedicalHistory">Past Medical History</Translate>
            </span>
          </dt>
          <dd>{treatmentPlanEntity.pastMedicalHistory}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="lucciadminApp.treatmentPlan.note">Note</Translate>
            </span>
          </dt>
          <dd>{treatmentPlanEntity.note}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatmentPlan.customer">Customer</Translate>
          </dt>
          <dd>{treatmentPlanEntity.customerName ? treatmentPlanEntity.customerName : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatmentPlan.service">Service</Translate>
          </dt>
          <dd>{treatmentPlanEntity.serviceName ? treatmentPlanEntity.serviceName : ''}</dd>
        </dl>
        <Button tag={Link} to="/treatment-plan" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/treatment-plan/${treatmentPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ treatmentPlan }: IRootState) => ({
  treatmentPlanEntity: treatmentPlan.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentPlanDetail);
