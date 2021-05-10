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
import { getEntity, updateEntity, createEntity, reset } from './achievement.reducer';
import { IAchievement } from 'app/shared/model/achievement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAchievementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AchievementUpdate = (props: IAchievementUpdateProps) => {
  const [imgUrlId, setImgUrlId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { achievementEntity, imgUrls, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/achievement');
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
      const entity = {
        ...achievementEntity,
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
          <h2 id="lucciadminApp.achievement.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.achievement.home.createOrEditLabel">Create or edit a Achievement</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : achievementEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="achievement-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="achievement-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="achievement-name">
                  <Translate contentKey="lucciadminApp.achievement.name">Name</Translate>
                </Label>
                <AvField
                  id="achievement-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="numberLabel" for="achievement-number">
                  <Translate contentKey="lucciadminApp.achievement.number">Number</Translate>
                </Label>
                <AvField
                  id="achievement-number"
                  type="string"
                  className="form-control"
                  name="number"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="achievement-imgUrl">
                  <Translate contentKey="lucciadminApp.achievement.imgUrl">Img Url</Translate>
                </Label>
                <AvInput id="achievement-imgUrl" type="select" className="form-control" name="imgUrl.id">
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
              <Button tag={Link} id="cancel-save" to="/achievement" replace color="info">
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
  achievementEntity: storeState.achievement.entity,
  loading: storeState.achievement.loading,
  updating: storeState.achievement.updating,
  updateSuccess: storeState.achievement.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(AchievementUpdate);
