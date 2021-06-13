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
import { IServiceItem } from 'app/shared/model/service-item.model';
import { getEntities as getServiceItems } from 'app/entities/service-item/service-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './blog.reducer';
import { IBlog } from 'app/shared/model/blog.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { toast } from 'react-toastify';
import { IMAGE_FILE_SYSTEM_URL } from 'app/config/constants';

export interface IBlogUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BlogUpdate = (props: IBlogUpdateProps) => {
  const [titleImgUrlId, setTitleImgUrlId] = useState('0');
  const [serviceItemId, setServiceItemId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { blogEntity, imgUrls, serviceItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/blog');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getImgUrls();
    props.getServiceItems();
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
    values.publishDate = convertDateTimeToServer(values.publishDate);

    if (errors.length === 0) {
      const entity = {
        ...blogEntity,
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
          <h2 id="lucciadminApp.blog.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.blog.home.createOrEditLabel">Create or edit a Blog</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : blogEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="blog-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="blog-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="blog-title">
                  <Translate contentKey="lucciadminApp.blog.title">Title</Translate>
                </Label>
                <AvField
                  id="blog-title"
                  type="text"
                  name="title"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="publishDateLabel" for="blog-publishDate">
                  <Translate contentKey="lucciadminApp.blog.publishDate">Publish Date</Translate>
                </Label>
                <AvInput
                  id="blog-publishDate"
                  type="datetime-local"
                  className="form-control"
                  name="publishDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.blogEntity.publishDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="blog-content">
                  <Translate contentKey="lucciadminApp.blog.content">Content</Translate>
                </Label>
                <AvField
                  id="blog-content"
                  type="textarea"
                  rows="10"
                  name="content"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="blog-description">
                  <Translate contentKey="lucciadminApp.blog.description">Description</Translate>
                </Label>
                <AvField id="blog-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="blog-titleImgUrl">
                  <Translate contentKey="lucciadminApp.blog.titleImgUrl">Title Img Url</Translate>
                </Label>
                <div className="form-group">
                  <input type="file" name="file" onChange={changeHandler}/>
                  {file && <p>Size in bytes: {file.size}</p>}
                  {blogEntity.titleImgUrl &&
                  <img src={`${IMAGE_FILE_SYSTEM_URL+blogEntity.titleImgUrl.imgUrl}`}
                       style={{maxWidth: 200, margin:20}} alt="hello world"/>}
                </div>
              </AvGroup>
              <AvGroup>
                <Label for="blog-serviceItem">
                  <Translate contentKey="lucciadminApp.blog.serviceItem">Service Item</Translate>
                </Label>
                <AvInput id="blog-serviceItem" type="select" className="form-control" name="serviceItem.id">
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
              <Button tag={Link} id="cancel-save" to="/blog" replace color="info">
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
  serviceItems: storeState.serviceItem.entities,
  blogEntity: storeState.blog.entity,
  loading: storeState.blog.loading,
  updating: storeState.blog.updating,
  updateSuccess: storeState.blog.updateSuccess,
});

const mapDispatchToProps = {
  getImgUrls,
  getServiceItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BlogUpdate);
