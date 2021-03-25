import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './material.reducer';
import { IMaterial } from 'app/shared/model/material.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMaterialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MaterialDetail = (props: IMaterialDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { materialEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.material.detail.title">Material</Translate> [<b>{materialEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="lucciadminApp.material.name">Name</Translate>
            </span>
          </dt>
          <dd>{materialEntity.name}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="lucciadminApp.material.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{materialEntity.amount}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="lucciadminApp.material.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{materialEntity.unit}</dd>
        </dl>
        <Button tag={Link} to="/material" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/material/${materialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ material }: IRootState) => ({
  materialEntity: material.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MaterialDetail);
