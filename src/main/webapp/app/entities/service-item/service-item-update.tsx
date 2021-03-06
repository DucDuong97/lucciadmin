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
import { IVideo } from 'app/shared/model/video.model';
import { getEntities as getVideos } from 'app/entities/video/video.reducer';
import { getEntity, updateEntity, createEntity, reset } from './service-item.reducer';
import { IServiceItem } from 'app/shared/model/service-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

import {IMAGE_FILE_SYSTEM_URL} from "app/config/constants";
import {toast} from "react-toastify";

export interface IServiceItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceItemUpdate = (props: IServiceItemUpdateProps) => {
  const [idscustomerImgUrls, setIdscustomerImgUrls] = useState([]);
  const [idsrelatedVideos, setIdsrelatedVideos] = useState([]);
  const [iconId, setIconId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceItemEntity, imgUrls, videos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/service-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getImgUrls();
    props.getVideos();
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
        ...serviceItemEntity,
        ...values,
        customerImgUrls: mapIdList(values.customerImgUrls),
        relatedVideos: mapIdList(values.relatedVideos),
        file
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
                <AvField id="service-item-description" type="textarea" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="service-item-icon">
                  <Translate contentKey="lucciadminApp.serviceItem.icon">Icon</Translate>
                </Label>
                <div className="form-group">
                  <input type="file" name="file" onChange={changeHandler}/>
                  {file && <p>Size in bytes: {file.size}</p>}
                  {serviceItemEntity.iconName &&
                  <img src={`${IMAGE_FILE_SYSTEM_URL}${serviceItemEntity.iconName}`}
                       style={{maxWidth: 200, margin:20}} alt="hello world"/>
                  }
                </div>
              </AvGroup>
              <AvGroup>
                <Label for="service-item-customerImgUrls">
                  <Translate contentKey="lucciadminApp.serviceItem.customerImgUrls">Customer Img Urls</Translate>
                </Label>
                <Row>
                  {serviceItemEntity.customerImgUrls
                    ? serviceItemEntity.customerImgUrls.map(otherEntity => (
                      <Col key={otherEntity.id} md="4">
                        {otherEntity &&
                        <img src={`${IMAGE_FILE_SYSTEM_URL}${otherEntity.imgUrl}`}
                             style={{maxWidth: 200, margin:20}} alt="hello world"/>
                        }
                      </Col>
                    ))
                    : null}
                </Row>
                <Button tag={Link} to={`/img-url?serviceId=${serviceItemEntity.id}`} color="warning" style={{marginTop: "0.5rem"}}>
                  <FontAwesomeIcon icon="plus" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.addimage">Add Image</Translate>
                </span>
                </Button>
              </AvGroup>
              <AvGroup>
                <Label for="service-item-relatedVideos">
                  <Translate contentKey="lucciadminApp.serviceItem.relatedVideos">Related Videos</Translate>
                </Label>
                <AvInput
                  id="service-item-relatedVideos"
                  type="select"
                  multiple
                  className="form-control"
                  name="relatedVideos"
                  value={serviceItemEntity.relatedVideos && serviceItemEntity.relatedVideos.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {videos
                    ? videos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
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
  videos: storeState.video.entities,
  serviceItemEntity: storeState.serviceItem.entity,
  loading: storeState.serviceItem.loading,
  updating: storeState.serviceItem.updating,
  updateSuccess: storeState.serviceItem.updateSuccess,
});

const mapDispatchToProps = {
  getImgUrls,
  getVideos,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceItemUpdate);
