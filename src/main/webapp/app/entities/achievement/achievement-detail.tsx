import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './achievement.reducer';
import { IAchievement } from 'app/shared/model/achievement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAchievementDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AchievementDetail = (props: IAchievementDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { achievementEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.achievement.detail.title">Achievement</Translate> [<b>{achievementEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="lucciadminApp.achievement.name">Name</Translate>
            </span>
          </dt>
          <dd>{achievementEntity.name}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lucciadminApp.achievement.number">Number</Translate>
            </span>
          </dt>
          <dd>{achievementEntity.number}</dd>
        </dl>
        <Button tag={Link} to="/achievement" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/achievement/${achievementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ achievement }: IRootState) => ({
  achievementEntity: achievement.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AchievementDetail);
