import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMaterial } from 'app/shared/model/material.model';
import { getEntities as getMaterials } from 'app/entities/material/material.reducer';
import { IReceptionist } from 'app/shared/model/receptionist.model';
import { getEntities as getReceptionists } from 'app/entities/receptionist/receptionist.reducer';
import { getEntity, updateEntity, createEntity, reset } from './get-material.reducer';
import { IGetMaterial } from 'app/shared/model/get-material.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGetMaterialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GetMaterialUpdate = (props: IGetMaterialUpdateProps) => {
  const [materialId, setMaterialId] = useState('0');
  const [receptionistId, setReceptionistId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { getMaterialEntity, materials, receptionists, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/get-material');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMaterials();
    props.getReceptionists();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...getMaterialEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lucciadminApp.getMaterial.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.getMaterial.home.createOrEditLabel">Create or edit a GetMaterial</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : getMaterialEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="get-material-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="get-material-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="get-material-date">
                  <Translate contentKey="lucciadminApp.getMaterial.date">Date</Translate>
                </Label>
                <AvInput
                  id="get-material-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.getMaterialEntity.date)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="get-material-material">
                  <Translate contentKey="lucciadminApp.getMaterial.material">Material</Translate>
                </Label>
                <AvInput id="get-material-material" type="select" className="form-control" name="material.id">
                  <option value="" key="0" />
                  {materials
                    ? materials.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="get-material-receptionist">
                  <Translate contentKey="lucciadminApp.getMaterial.receptionist">Receptionist</Translate>
                </Label>
                <AvInput id="get-material-receptionist" type="select" className="form-control" name="receptionist.id">
                  <option value="" key="0" />
                  {receptionists
                    ? receptionists.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/get-material" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  materials: storeState.material.entities,
  receptionists: storeState.receptionist.entities,
  getMaterialEntity: storeState.getMaterial.entity,
  loading: storeState.getMaterial.loading,
  updating: storeState.getMaterial.updating,
  updateSuccess: storeState.getMaterial.updateSuccess,
});

const mapDispatchToProps = {
  getMaterials,
  getReceptionists,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GetMaterialUpdate);
