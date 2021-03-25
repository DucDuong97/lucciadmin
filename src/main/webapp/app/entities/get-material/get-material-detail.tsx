import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './get-material.reducer';
import { IGetMaterial } from 'app/shared/model/get-material.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGetMaterialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GetMaterialDetail = (props: IGetMaterialDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { getMaterialEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.getMaterial.detail.title">GetMaterial</Translate> [<b>{getMaterialEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="lucciadminApp.getMaterial.date">Date</Translate>
            </span>
          </dt>
          <dd>{getMaterialEntity.date ? <TextFormat value={getMaterialEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.getMaterial.material">Material</Translate>
          </dt>
          <dd>{getMaterialEntity.material ? getMaterialEntity.material.id : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.getMaterial.receptionist">Receptionist</Translate>
          </dt>
          <dd>{getMaterialEntity.receptionist ? getMaterialEntity.receptionist.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/get-material" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/get-material/${getMaterialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ getMaterial }: IRootState) => ({
  getMaterialEntity: getMaterial.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GetMaterialDetail);
