import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-item.reducer';
import { IServiceItem } from 'app/shared/model/service-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceItemDetail = (props: IServiceItemDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceItemEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.serviceItem.detail.title">ServiceItem</Translate> [<b>{serviceItemEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="lucciadminApp.serviceItem.name">Name</Translate>
            </span>
          </dt>
          <dd>{serviceItemEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="lucciadminApp.serviceItem.description">Description</Translate>
            </span>
          </dt>
          <dd>{serviceItemEntity.description}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.serviceItem.imgUrl">Img Url</Translate>
          </dt>
          <dd>{serviceItemEntity.imgUrl ? serviceItemEntity.imgUrl.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-item" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-item/${serviceItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceItem }: IRootState) => ({
  serviceItemEntity: serviceItem.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceItemDetail);
