import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './treatment-history.reducer';
import { ITreatmentHistory } from 'app/shared/model/treatment-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITreatmentHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TreatmentHistoryDetail = (props: ITreatmentHistoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { treatmentHistoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.treatmentHistory.detail.title">TreatmentHistory</Translate> [
          <b>{treatmentHistoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="lucciadminApp.treatmentHistory.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {treatmentHistoryEntity.date ? <TextFormat value={treatmentHistoryEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="lucciadminApp.treatmentHistory.treatment">Treatment</Translate>
          </dt>
          <dd>{treatmentHistoryEntity.treatment ? treatmentHistoryEntity.treatment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/treatment-history" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/treatment-history/${treatmentHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ treatmentHistory }: IRootState) => ({
  treatmentHistoryEntity: treatmentHistory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentHistoryDetail);
