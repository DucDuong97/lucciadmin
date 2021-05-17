import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './process.reducer';
import { IProcess } from 'app/shared/model/process.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcessDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessDetail = (props: IProcessDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { processEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.process.detail.title">Process</Translate> [<b>{processEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="lucciadminApp.process.name">Name</Translate>
            </span>
          </dt>
          <dd>{processEntity.name}</dd>
          <dt>
            <span id="order">
              <Translate contentKey="lucciadminApp.process.order">Order</Translate>
            </span>
          </dt>
          <dd>{processEntity.order}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.process.serviceItem">Service Item</Translate>
          </dt>
          <dd>{processEntity.serviceItem ? processEntity.serviceItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/process" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/process/${processEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ process }: IRootState) => ({
  processEntity: process.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProcessDetail);
