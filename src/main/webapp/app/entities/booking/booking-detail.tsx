import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity, assignDoctor } from './booking.reducer';
import { getDoctorsForBooking as getDoctors } from '../employee/employee.reducer';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, APP_TIME_FORMAT} from 'app/config/constants';

export interface IBookingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingDetail = (props: IBookingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
    props.getDoctors();
  }, []);

  const { bookingEntity,  doctors } = props;

  const assignBookingToDoctor = event => {
    props.assignToDoctor({
      ...bookingEntity,
      correspondDoctorId: event.target.value
    });
  }

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
          {props.isOperationsDirector || props.isBranchBossDoctor ?
            <select value={bookingEntity.correspondDoctorId} onChange={assignBookingToDoctor}>
              <option value="" key="0" />
              {doctors
                ? doctors.map(otherEntity => (
                  <option value={otherEntity.id} key={otherEntity.id}>
                    {otherEntity.name + " - " + otherEntity.id}
                  </option>
                ))
                : null}
            </select>
          :
            <dd>{bookingEntity.correspondDoctorId ? bookingEntity.correspondDoctorId : ''}</dd>
          }
          <dt>
            <Translate contentKey="lucciadminApp.booking.customer">Customer</Translate>
          </dt>
          <dd>{bookingEntity.customerId ? <Link to={`/customer/${bookingEntity.customerId}`}>{bookingEntity.customerId}</Link> : ''}</dd>
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

const mapStateToProps = ({ booking, employee, authentication }: IRootState) => ({
  bookingEntity: booking.entity,
  doctors: employee.entities,
  isOperationsDirector: authentication.isOperationsDirector,
  isBranchBossDoctor: authentication.isBranchBossDoctor,
});

const mapDispatchToProps = { getEntity, getDoctors, assignToDoctor: assignDoctor };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingDetail);
