import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IConsult } from 'app/shared/model/consult.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, cancelEntity } from './consult.reducer';

export interface IConsultCancelDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsultCancelDialog = (props: IConsultCancelDialogProps) => {
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
    props.cancelEntity(props.consultEntity.id);
  };

  const { consultEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.cancel.title">Confirm cancel operation</Translate>
      </ModalHeader>
      <ModalBody id="lucciadminApp.consult.cancel.question">
        <Translate contentKey="lucciadminApp.consult.cancel.question" interpolate={{ id: consultEntity.id }}>
          Are you sure you want to cancel this Consult?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-cancel-consult" color="danger" onClick={confirmCancel}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ consult }: IRootState) => ({
  consultEntity: consult.entity,
  updateSuccess: consult.updateSuccess,
});

const mapDispatchToProps = { getEntity, cancelEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultCancelDialog);
