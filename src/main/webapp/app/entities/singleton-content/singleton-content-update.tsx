import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './singleton-content.reducer';
import { ISingletonContent } from 'app/shared/model/singleton-content.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import {IMAGE_FILE_SYSTEM_URL} from "app/config/constants";
import { toast } from 'react-toastify';

export interface ISingletonContentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SingletonContentUpdate = (props: ISingletonContentUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { singletonContentEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/singleton-content');
  };

  const [type, setType] = useState('PHONE');

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
      setType(singletonContentEntity.type);
    }
  }, []);

  useEffect(() => {
    setType(singletonContentEntity.type);
  }, [singletonContentEntity]);

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
        ...singletonContentEntity,
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
          <h2 id="lucciadminApp.singletonContent.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.singletonContent.home.createOrEditLabel">Create or edit a SingletonContent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : singletonContentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="singleton-content-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="singleton-content-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="singleton-content-type">
                  <Translate contentKey="lucciadminApp.singletonContent.type">Type</Translate>
                </Label>
                <AvInput
                  id="singleton-content-type"
                  type="select"
                  className="form-control"
                  name="type"
                  onChange={event => setType(event.target.value)}
                  value={(!isNew && singletonContentEntity.type) || 'PHONE'}
                >
                  <option value="PHONE">{translate('lucciadminApp.ContentType.PHONE')}</option>
                  <option value="EMAIL">{translate('lucciadminApp.ContentType.EMAIL')}</option>
                  <option value="ADDRESS">{translate('lucciadminApp.ContentType.ADDRESS')}</option>
                  <option value="INTRO">{translate('lucciadminApp.ContentType.INTRO')}</option>
                  <option value="IMG_HOME_BANNER">{translate('lucciadminApp.ContentType.IMG_HOME_BANNER')}</option>
                  <option value="IMG_CONTACT_BANNER">{translate('lucciadminApp.ContentType.IMG_CONTACT_BANNER')}</option>
                  <option value="IMG_ACHIEVEMENT_BANNER">{translate('lucciadminApp.ContentType.IMG_ACHIEVEMENT_BANNER')}</option>
                  <option value="IMG_LOGO">{translate('lucciadminApp.ContentType.IMG_LOGO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="singleton-content-content">
                  <Translate contentKey="lucciadminApp.singletonContent.content">Content</Translate>
                </Label>
                {type && type.startsWith('IMG') &&
                <div className="form-group">
                  <input type="file" name="file" onChange={changeHandler}/>
                  {file && <p>Size in bytes: {file.size}</p>}
                  {singletonContentEntity.content &&
                    <img src={`${IMAGE_FILE_SYSTEM_URL+singletonContentEntity.content}`}
                         style={{maxWidth: 200, margin:20}} alt="hello world"/>
                  }
                </div>
                }
                <AvField id="singleton-content-content" type="text" name="content" disabled={type && type.startsWith('IMG')}/>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/singleton-content" replace color="info">
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
  singletonContentEntity: storeState.singletonContent.entity,
  loading: storeState.singletonContent.loading,
  updating: storeState.singletonContent.updating,
  updateSuccess: storeState.singletonContent.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SingletonContentUpdate);
