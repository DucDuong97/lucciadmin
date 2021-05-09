import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './img-url.reducer';
import { IImgUrl } from 'app/shared/model/img-url.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IImgUrlDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ImgUrlDetail = (props: IImgUrlDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { imgUrlEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.imgUrl.detail.title">ImgUrl</Translate> [<b>{imgUrlEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="imgUrl">
              <Translate contentKey="lucciadminApp.imgUrl.imgUrl">Img Url</Translate>
            </span>
          </dt>
          <dd>{imgUrlEntity.imgUrl}</dd>
        </dl>
        <Button tag={Link} to="/img-url" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/img-url/${imgUrlEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ imgUrl }: IRootState) => ({
  imgUrlEntity: imgUrl.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ImgUrlDetail);
