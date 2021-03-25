import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './receptionist.reducer';
import { IReceptionist } from 'app/shared/model/receptionist.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IReceptionistDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ReceptionistDetail = (props: IReceptionistDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { receptionistEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.receptionist.detail.title">Receptionist</Translate> [<b>{receptionistEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="salary">
              <Translate contentKey="lucciadminApp.receptionist.salary">Salary</Translate>
            </span>
          </dt>
          <dd>{receptionistEntity.salary}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.receptionist.person">Person</Translate>
          </dt>
          <dd>{receptionistEntity.person ? receptionistEntity.person.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/receptionist" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/receptionist/${receptionistEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ receptionist }: IRootState) => ({
  receptionistEntity: receptionist.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReceptionistDetail);
