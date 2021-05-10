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
import { getEntity, updateEntity, createEntity, reset } from './customer-review.reducer';
import { ICustomerReview } from 'app/shared/model/customer-review.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICustomerReviewUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CustomerReviewUpdate = (props: ICustomerReviewUpdateProps) => {
  const [customerImgUrlId, setCustomerImgUrlId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { customerReviewEntity, imgUrls, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/customer-review');
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
        ...customerReviewEntity,
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
          <h2 id="lucciadminApp.customerReview.home.createOrEditLabel">
            <Translate contentKey="lucciadminApp.customerReview.home.createOrEditLabel">Create or edit a CustomerReview</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : customerReviewEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="customer-review-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="customer-review-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="customerNameLabel" for="customer-review-customerName">
                  <Translate contentKey="lucciadminApp.customerReview.customerName">Customer Name</Translate>
                </Label>
                <AvField
                  id="customer-review-customerName"
                  type="text"
                  name="customerName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="customerAddressLabel" for="customer-review-customerAddress">
                  <Translate contentKey="lucciadminApp.customerReview.customerAddress">Customer Address</Translate>
                </Label>
                <AvField id="customer-review-customerAddress" type="text" name="customerAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="commentLabel" for="customer-review-comment">
                  <Translate contentKey="lucciadminApp.customerReview.comment">Comment</Translate>
                </Label>
                <AvField
                  id="customer-review-comment"
                  type="text"
                  name="comment"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="customer-review-customerImgUrl">
                  <Translate contentKey="lucciadminApp.customerReview.customerImgUrl">Customer Img Url</Translate>
                </Label>
                <AvInput id="customer-review-customerImgUrl" type="select" className="form-control" name="customerImgUrl.id">
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
              <Button tag={Link} id="cancel-save" to="/customer-review" replace color="info">
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
  customerReviewEntity: storeState.customerReview.entity,
  loading: storeState.customerReview.loading,
  updating: storeState.customerReview.updating,
  updateSuccess: storeState.customerReview.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(CustomerReviewUpdate);
