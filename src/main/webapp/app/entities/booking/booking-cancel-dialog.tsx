import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBooking } from 'app/shared/model/booking.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, cancelEntity } from './booking.reducer';

export interface IBookingCancelDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingCancelDialog = (props: IBookingCancelDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.goBack();
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmCancel = () => {
    props.cancelEntity(props.bookingEntity.id);
  };

  const { bookingEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.cancel.title">Confirm cancel operation</Translate>
      </ModalHeader>
      <ModalBody id="lucciadminApp.booking.cancel.question">
        <Translate contentKey="lucciadminApp.booking.cancel.question" interpolate={{ id: bookingEntity.id }}>
          Are you sure you want to cancel this Booking?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-cancel-booking" color="danger" onClick={confirmCancel}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ booking }: IRootState) => ({
  bookingEntity: booking.entity,
  updateSuccess: booking.updateSuccess,
});

const mapDispatchToProps = { getEntity, cancelEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingCancelDialog);
