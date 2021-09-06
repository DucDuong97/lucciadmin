import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './potential.reducer';
import { IPotential } from 'app/shared/model/potential.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPotentialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PotentialDetail = (props: IPotentialDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { potentialEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.potential.detail.title">Potential</Translate> [<b>{potentialEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="lucciadminApp.potential.name">Name</Translate>
            </span>
          </dt>
          <dd>{potentialEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="lucciadminApp.potential.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{potentialEntity.phone}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="lucciadminApp.potential.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{potentialEntity.gender ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.potential.service">Service</Translate>
          </dt>
          <dd>{potentialEntity.serviceName ? potentialEntity.serviceName : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.potential.branch">Branch</Translate>
          </dt>
          <dd>{potentialEntity.branchAdress ? potentialEntity.branchAdress : ''}</dd>
        </dl>
        <Button tag={Link} to="/potential" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/potential/${potentialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ potential }: IRootState) => ({
  potentialEntity: potential.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PotentialDetail);
