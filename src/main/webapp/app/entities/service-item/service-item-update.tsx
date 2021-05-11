import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IImgUrl } from 'app/shared/model/img-url.model';
import { getEntities as getImgUrls } from 'app/entities/img-url/img-url.reducer';
import { getEntity, updateEntity, createEntity, reset } from './service-item.reducer';
import { IServiceItem } from 'app/shared/model/service-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceItemUpdate = (props: IServiceItemUpdateProps) => {
  const [imgUrlId, setImgUrlId] = useState('0');
  const [customerImgUrlsId, setCustomerImgUrlsId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceItemEntity, imgUrls, loading, updating } = props;

  const handleClose = () => {
    props.history.goBack();
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getImgUrls();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const customerImgs = [];
      values.customerImgUrls.forEach(value => {
        customerImgs.push({id: value});
      });
      const entity = {
        ...serviceItemEntity,
        ...values,
        customerImgUrls: customerImgs
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
          <h2 id="lucciadminApp.serviceItem.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.serviceItem.home.createOrEditLabel">Create or edit a ServiceItem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serviceItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="service-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="service-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="service-item-name">
                  <Translate contentKey="lucciadminApp.serviceItem.name">Name</Translate>
                </Label>
                <AvField
                  id="service-item-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="service-item-description">
                  <Translate contentKey="lucciadminApp.serviceItem.description">Description</Translate>
                </Label>
                <AvField id="service-item-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="service-item-imgUrl">
                  <Translate contentKey="lucciadminApp.serviceItem.imgUrl">Img Url</Translate>
                </Label>
                <AvInput id="service-item-imgUrl" type="select" className="form-control" name="imgUrl.id">
                  <option value="" key="0" />
                  {imgUrls
                    ? imgUrls.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.imgUrl}
                        </option>
                      ))
                    : null}
                </AvInput>
                <Button tag={Link} to={`/img-url/new`} color="primary" size="sm">
                  <FontAwesomeIcon icon="plus" />{' '}
                  <span className="d-none d-md-inline">
                    Add Image URL
                  </span>
                </Button>
              </AvGroup>
              {/*multiple selection of customerImgs*/}
              <AvGroup>
                <Label for="customerImgUrls">
                  <Translate contentKey="lucciadminApp.serviceItem.customerImgUrls">Customer Image URLS</Translate>
                </Label>
                <AvInput type="select" className="form-control"
                         name="customerImgUrls" value={serviceItemEntity.customerImgUrls ? serviceItemEntity.customerImgUrls.map(img => img.id) : []}
                         multiple>
                  {imgUrls
                    ? imgUrls.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.imgUrl}
                      </option>
                    ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/service-item" replace color="info">
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
  imgUrls: storeState.imgUrl.entities,
  serviceItemEntity: storeState.serviceItem.entity,
  loading: storeState.serviceItem.loading,
  updating: storeState.serviceItem.updating,
  updateSuccess: storeState.serviceItem.updateSuccess,
});

const mapDispatchToProps = {
  getImgUrls,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceItemUpdate);
