import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './booking.reducer';
import { IBooking } from 'app/shared/model/booking.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, APP_TIME_FORMAT} from 'app/config/constants';

export interface IBookingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingDetail = (props: IBookingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bookingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.booking.detail.title">Booking</Translate> [<b>{bookingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="lucciadminApp.booking.date">Date</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.date ? <TextFormat value={bookingEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="time">
              <Translate contentKey="lucciadminApp.booking.time">Time</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.time ? <TextFormat value={bookingEntity.time} type="number" format={APP_TIME_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.booking.correspondDoctor">Correspond Doctor</Translate>
          </dt>
          <dd>{bookingEntity.correspondDoctorId ? bookingEntity.correspondDoctorId : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.booking.customer">Customer</Translate>
          </dt>
          <dd>{bookingEntity.customerId ? bookingEntity.customerId : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.booking.branch">Branch</Translate>
          </dt>
          <dd>{bookingEntity.branchId ? bookingEntity.branchId : ''}</dd>
        </dl>
        <Button tag={Link} to="/booking" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/booking/${bookingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ booking }: IRootState) => ({
  bookingEntity: booking.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingDetail);
