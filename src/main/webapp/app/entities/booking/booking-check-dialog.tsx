import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBooking } from 'app/shared/model/booking.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, checkEntity } from './booking.reducer';

export interface IBookingCheckDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingCheckDialog = (props: IBookingCheckDialogProps) => {

  const [planId, setPlanId] = useState(null);

  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.goBack();
  };

  const handleCloseUpdateSuccess = () => {
    console.log(props.bookingEntity);
    props.history.push('/treatment-plan/' + planId);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleCloseUpdateSuccess();
    }
  }, [props.updateSuccess]);

  const confirmCheck = () => {
    setPlanId(props.bookingEntity.treatmentPlanId);
    props.checkEntity(props.bookingEntity.id);
  };

  const { bookingEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.check.title">Confirm check operation</Translate>
      </ModalHeader>
      <ModalBody id="lucciadminApp.booking.check.question">
        <Translate contentKey="lucciadminApp.booking.check.question" interpolate={{ id: bookingEntity.id }}>
          Are you sure you want to check this Booking?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-booking" color="success" onClick={confirmCheck}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.check">Check</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ booking }: IRootState) => ({
  bookingEntity: booking.entity,
  updateSuccess: booking.updateSuccess,
});

const mapDispatchToProps = { getEntity, checkEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingCheckDialog);
