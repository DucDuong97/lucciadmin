import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IConsult } from 'app/shared/model/consult.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, checkEntity } from './consult.reducer';

export interface IConsultCheckDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsultCheckDialog = (props: IConsultCheckDialogProps) => {

  const [planId, setPlanId] = useState(null);

  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.goBack();
  };

  const handleCloseUpdateSuccess = () => {
    console.log(props.consultEntity);
    props.history.goBack();
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleCloseUpdateSuccess();
    }
  }, [props.updateSuccess]);

  const confirmCheck = () => {
    setPlanId(props.consultEntity.customerId);
    props.checkEntity(props.consultEntity.id);
  };

  const { consultEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.check.title">Confirm check operation</Translate>
      </ModalHeader>
      <ModalBody id="lucciadminApp.consult.check.question">
        <Translate contentKey="lucciadminApp.consult.check.question" interpolate={{ id: consultEntity.id }}>
          Are you sure you want to check this Consult?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-consult" color="success" onClick={confirmCheck}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.check">Check</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ consult }: IRootState) => ({
  consultEntity: consult.entity,
  updateSuccess: consult.updateSuccess,
});

const mapDispatchToProps = { getEntity, checkEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConsultCheckDialog);
