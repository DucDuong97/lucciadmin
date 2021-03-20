import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './page.reducer';
import { IPage } from 'app/shared/model/page.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PageUpdate = (props: IPageUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { pageEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/page');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...pageEntity,
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
          <h2 id="lucciadminApp.page.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.page.home.createOrEditLabel">Create or edit a Page</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="page-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="page-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="pageLabel" for="page-page">
                  <Translate contentKey="lucciadminApp.page.page">Page</Translate>
                </Label>
                <AvInput id="page-page" type="select" className="form-control" name="page" value={(!isNew && pageEntity.page) || 'HOME'}>
                  <option value="HOME">{translate('lucciadminApp.SubPage.HOME')}</option>
                  <option value="INTRO">{translate('lucciadminApp.SubPage.INTRO')}</option>
                  <option value="SERVICE">{translate('lucciadminApp.SubPage.SERVICE')}</option>
                  <option value="BLOG">{translate('lucciadminApp.SubPage.BLOG')}</option>
                  <option value="CONTACT">{translate('lucciadminApp.SubPage.CONTACT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="page-description">
                  <Translate contentKey="lucciadminApp.page.description">Description</Translate>
                </Label>
                <AvField id="page-description" type="text" name="description" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/page" replace color="info">
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
  pageEntity: storeState.page.entity,
  loading: storeState.page.loading,
  updating: storeState.page.updating,
  updateSuccess: storeState.page.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PageUpdate);
