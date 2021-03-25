import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './accountant.reducer';
import { IAccountant } from 'app/shared/model/accountant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAccountantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AccountantDetail = (props: IAccountantDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { accountantEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.accountant.detail.title">Accountant</Translate> [<b>{accountantEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="salary">
              <Translate contentKey="lucciadminApp.accountant.salary">Salary</Translate>
            </span>
          </dt>
          <dd>{accountantEntity.salary}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.accountant.person">Person</Translate>
          </dt>
          <dd>{accountantEntity.person ? accountantEntity.person.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/accountant" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/accountant/${accountantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ accountant }: IRootState) => ({
  accountantEntity: accountant.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AccountantDetail);
