import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './consult.reducer';
import { IConsult } from 'app/shared/model/consult.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConsultDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsultDetail = (props: IConsultDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { consultEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.consult.detail.title">Consult</Translate> [<b>{consultEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="time">
              <Translate contentKey="lucciadminApp.consult.time">Time</Translate>
            </span>
          </dt>
          <dd>{consultEntity.time ? <TextFormat value={consultEntity.time} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="lucciadminApp.consult.note">Note</Translate>
            </span>
          </dt>
          <dd>{consultEntity.note}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.consult.customer">Customer</Translate>
          </dt>
          <dd>{consultEntity.customerName ? consultEntity.customerName : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.consult.branch">Branch</Translate>
          </dt>
          <dd>{consultEntity.branchAdress ? consultEntity.branchAdress : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.consult.consultingDoctor">Consulting Doctor</Translate>
          </dt>
          <dd>{consultEntity.consultingDoctorName ? consultEntity.consultingDoctorName : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.consult.service">Service</Translate>
          </dt>
          <dd>
            {consultEntity.services
              ? consultEntity.services.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {consultEntity.services && i === consultEntity.services.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ consult }: IRootState) => ({
  consultEntity: consult.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultDetail);
