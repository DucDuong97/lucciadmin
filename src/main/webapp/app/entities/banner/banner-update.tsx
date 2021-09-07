import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IImgUrl } from 'app/shared/model/img-url.model';
import { getEntities as getImgUrls } from 'app/entities/img-url/img-url.reducer';
import { getEntity, updateEntity, createEntity, reset } from './banner.reducer';
import { IBanner } from 'app/shared/model/banner.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import {toast} from "react-toastify";
import {IMAGE_FILE_SYSTEM_URL} from "app/config/constants";

export interface IBannerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BannerUpdate = (props: IBannerUpdateProps) => {
  const [imgUrlId, setImgUrlId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bannerEntity, imgUrls, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/banner');
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
        ...bannerEntity,
        ...values,
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
          <h2 id="lucciadminApp.banner.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.banner.home.createOrEditLabel">Create or edit a Banner</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bannerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="banner-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="banner-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="service-item-icon">
                  <Translate contentKey="lucciadminApp.serviceItem.icon">Icon</Translate>
                </Label>
                <div className="form-group">
                  <input type="file" name="file" onChange={changeHandler}/>
                  {file && <p>Size in bytes: {file.size}</p>}
                  {bannerEntity.imgUrlName &&
                  <img src={`${IMAGE_FILE_SYSTEM_URL}${bannerEntity.imgUrlName}`}
                       style={{maxWidth: 200, margin:20}} alt="hello world"/>
                  }
                </div>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/banner" replace color="info">
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
  bannerEntity: storeState.banner.entity,
  loading: storeState.banner.loading,
  updating: storeState.banner.updating,
  updateSuccess: storeState.banner.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(BannerUpdate);
