import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-option.reducer';
import { IServiceOption } from 'app/shared/model/service-option.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceOptionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceOptionDetail = (props: IServiceOptionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceOptionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.serviceOption.detail.title">ServiceOption</Translate> [<b>{serviceOptionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="lucciadminApp.serviceOption.name">Name</Translate>
            </span>
          </dt>
          <dd>{serviceOptionEntity.name}</dd>
          <dt>
            <span id="benefits">
              <Translate contentKey="lucciadminApp.serviceOption.benefits">Benefits</Translate>
            </span>
          </dt>
          <dd>{serviceOptionEntity.benefits}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="lucciadminApp.serviceOption.price">Price</Translate>
            </span>
          </dt>
          <dd>{serviceOptionEntity.price}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.serviceOption.service">Service</Translate>
          </dt>
          <dd>{serviceOptionEntity.service ? serviceOptionEntity.service.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-option" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-option/${serviceOptionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceOption }: IRootState) => ({
  serviceOptionEntity: serviceOption.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceOptionDetail);
