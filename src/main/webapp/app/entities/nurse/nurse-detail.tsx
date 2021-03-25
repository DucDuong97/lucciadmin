import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nurse.reducer';
import { INurse } from 'app/shared/model/nurse.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INurseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NurseDetail = (props: INurseDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { nurseEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.nurse.detail.title">Nurse</Translate> [<b>{nurseEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="salary">
              <Translate contentKey="lucciadminApp.nurse.salary">Salary</Translate>
            </span>
          </dt>
          <dd>{nurseEntity.salary}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.nurse.person">Person</Translate>
          </dt>
          <dd>{nurseEntity.person ? nurseEntity.person.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/nurse" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nurse/${nurseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ nurse }: IRootState) => ({
  nurseEntity: nurse.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NurseDetail);
