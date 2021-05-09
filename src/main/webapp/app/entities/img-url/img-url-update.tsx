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
import { getEntity, updateEntity, createEntity, reset } from './img-url.reducer';
import { IImgUrl } from 'app/shared/model/img-url.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IImgUrlUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ImgUrlUpdate = (props: IImgUrlUpdateProps) => {
  const [serviceItemId, setServiceItemId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { imgUrlEntity, serviceItems, loading, updating } = props;

  const handleClose = () => {
    props.history.goBack();
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...imgUrlEntity,
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
          <h2 id="lucciadminApp.imgUrl.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.imgUrl.home.createOrEditLabel">Create or edit a ImgUrl</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : imgUrlEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="img-url-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="img-url-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="imgUrlLabel" for="img-url-imgUrl">
                  <Translate contentKey="lucciadminApp.imgUrl.imgUrl">Img Url</Translate>
                </Label>
                <AvField
                  id="img-url-imgUrl"
                  type="text"
                  name="imgUrl"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/img-url" replace color="info">
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
  imgUrlEntity: storeState.imgUrl.entity,
  loading: storeState.imgUrl.loading,
  updating: storeState.imgUrl.updating,
  updateSuccess: storeState.imgUrl.updateSuccess,
});

const mapDispatchToProps = {
  getServiceItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ImgUrlUpdate);
