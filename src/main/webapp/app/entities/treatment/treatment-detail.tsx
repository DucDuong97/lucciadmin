import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment.reducer';
import { ITreatment } from 'app/shared/model/treatment.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, IMAGE_FILE_SYSTEM_URL} from 'app/config/constants';

export interface ITreatmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TreatmentDetail = (props: ITreatmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { treatmentEntity } = props;
  return (
    <Row>
      <Col md="12">
        <h2>
          <Translate contentKey="lucciadminApp.treatment.detail.title">Treatment</Translate> [<b>{treatmentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="description">
              <Translate contentKey="lucciadminApp.treatment.description">Description</Translate>
            </span>
          </dt>
          <dd>{treatmentEntity.description}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="lucciadminApp.treatment.date">Date</Translate>
            </span>
          </dt>
          <dd>{treatmentEntity.date ? <TextFormat value={treatmentEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="nextPlan">
              <Translate contentKey="lucciadminApp.treatment.nextPlan">Next Plan</Translate>
            </span>
          </dt>
          <dd>{treatmentEntity.nextPlan}</dd>
          <dt>
            <span id="revisitDate">
              <Translate contentKey="lucciadminApp.treatment.revisitDate">Revisit Date</Translate>
            </span>
          </dt>
          <dd>
            {treatmentEntity.revisitDate ? (
              <TextFormat value={treatmentEntity.revisitDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatment.doctor">Doctor</Translate>
          </dt>
          <dd>{treatmentEntity.doctorName ? treatmentEntity.doctorName : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatment.treatmentImgUrl">Treatment Img Url</Translate>
          </dt>
          <dd>
            <Row>
            {treatmentEntity.treatmentImgUrls
              ? treatmentEntity.treatmentImgUrls.map((val, i) => (
                <Col key={val.id} md="3">
                  {val &&
                  <img src={`${IMAGE_FILE_SYSTEM_URL}${val.path}/${val.name}`}
                       style={{maxWidth: 200, margin:20}} alt="hello world"/>
                  }
                </Col>
                ))
              : null}
            </Row>
          </dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatment.treatmentPlan">Treatment Plan</Translate>
          </dt>
          <dd>{treatmentEntity.treatmentPlanId ? treatmentEntity.treatmentPlanId : ''}</dd>
        </dl>
        <Button tag={Link} to={`/treatment/${treatmentEntity.id}/edit`} color="primary">
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
