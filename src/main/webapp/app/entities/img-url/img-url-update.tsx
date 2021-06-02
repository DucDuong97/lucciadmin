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
import {getEntity, updateEntity, createEntity, reset, uploadImage} from './img-url.reducer';
import { IImgUrl } from 'app/shared/model/img-url.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import {toast} from "react-toastify";

export interface IImgUrlUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ImgUrlUpdate = (props: IImgUrlUpdateProps) => {

  const { loading, updating } = props;

  const handleClose = () => {
    props.history.goBack();
  };

  useEffect(() => {
    props.reset();

    props.getServiceItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const [selectedFile, setSelectedFile] = useState<File>();
  const [isFilePicked, setIsFilePicked] = useState(false);

  const changeHandler = (event) => {
    const imgType = event.target.files[0].type.split('/')[0];
    if (imgType !== 'image') {
      setIsFilePicked(false);
      toast.error("Wrong file format");
      return;
    }
    setSelectedFile(event.target.files[0]);
    setIsFilePicked(true);
  };
  const saveEntity = (event, errors, values) => {
    if (!isFilePicked) {
      toast.error("Please choose an image");
      return;
    }
    props.uploadImage({imageFile: selectedFile});
    // const formData = new FormData();
    // formData.append('image', selectedFile);
    // axios.post('api/img-urls/upload', formData, {
    //   headers: {
    //     'Content-Type': 'multipart/form-data'
    //   }
    // });
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
            <AvForm onSubmit={saveEntity}>
              <input type="file" name="file" onChange={changeHandler} />
              {isFilePicked ? (
                <div>
                  <p>Filename: {selectedFile.name}</p>
                  <p>Size in bytes: {selectedFile.size}</p>
                </div>
              ) : (
                <p>Select a file to show details</p>
              )}
              <Button tag={Link} id="cancel-save" to="/img-url" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating || !isFilePicked}>
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
  uploadImage,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ImgUrlUpdate);
