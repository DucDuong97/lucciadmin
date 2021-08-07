import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceItem } from 'app/shared/model/service-item.model';
import { getEntities as getServiceItems } from 'app/entities/service-item/service-item.reducer';
import { IImgUrl } from 'app/shared/model/img-url.model';
import { getEntities as getImgUrls } from 'app/entities/img-url/img-url.reducer';
import { getEntity, updateEntity, createEntity, reset } from './process.reducer';
import { IProcess } from 'app/shared/model/process.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { toast } from 'react-toastify';
import { IMAGE_FILE_SYSTEM_URL } from 'app/config/constants';

export interface IProcessUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessUpdate = (props: IProcessUpdateProps) => {
  const [serviceItemId, setServiceItemId] = useState('0');
  const [iconId, setIconId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { processEntity, serviceItems, imgUrls, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/process');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceItems();
    props.getImgUrls();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const [file, setFile] = useState<File>();
  const changeHandler = (event) => {
    const imgType = event.target.files[0].type.split('/')[0];
    if (imgType !== 'image') {
      toast.error("Wrong file format");
      return;
    }
    setFile(event.target.files[0]);
  };

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...processEntity,
        ...values,
        file,
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
          <h2 id="lucciadminApp.process.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.process.home.createOrEditLabel">Create or edit a Process</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : processEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="process-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="process-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="process-name">
                  <Translate contentKey="lucciadminApp.process.name">Name</Translate>
                </Label>
                <AvField
                  id="process-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="orderLabel" for="process-order">
                  <Translate contentKey="lucciadminApp.process.order">Order</Translate>
                </Label>
                <AvField id="process-order" type="string" className="form-control" name="order" />
              </AvGroup>
              <AvGroup>
                <Label for="process-serviceItem">
                  <Translate contentKey="lucciadminApp.process.serviceItem">Service Item</Translate>
                </Label>
                <AvInput id="process-serviceItem" type="select" className="form-control" name="serviceItemId">
                  <option value="" key="0" />
                  {serviceItems
                    ? serviceItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="process-icon">
                  <Translate contentKey="lucciadminApp.process.icon">Icon</Translate>
                </Label>
                <div className="form-group">
                  <input type="file" name="file" onChange={changeHandler}/>
                  {file && <p>Size in bytes: {file.size}</p>}
                  {processEntity.iconId &&
                  <img src={`${IMAGE_FILE_SYSTEM_URL+processEntity.iconName}`}
                       style={{maxWidth: 200, margin:20}} alt="hello world"/>}
                </div>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/process" replace color="info">
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
  serviceItems: storeState.serviceItem.entities,
  imgUrls: storeState.imgUrl.entities,
  processEntity: storeState.process.entity,
  loading: storeState.process.loading,
  updating: storeState.process.updating,
  updateSuccess: storeState.process.updateSuccess,
});

const mapDispatchToProps = {
  getServiceItems,
  getImgUrls,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProcessUpdate);
