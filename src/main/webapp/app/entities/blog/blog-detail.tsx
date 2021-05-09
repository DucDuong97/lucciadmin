import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './blog.reducer';
import { IBlog } from 'app/shared/model/blog.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBlogDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BlogDetail = (props: IBlogDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { blogEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="lucciadminApp.blog.detail.title">Blog</Translate> [<b>{blogEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="lucciadminApp.blog.title">Title</Translate>
            </span>
          </dt>
          <dd>{blogEntity.title}</dd>
          <dt>
            <span id="publishDate">
              <Translate contentKey="lucciadminApp.blog.publishDate">Publish Date</Translate>
            </span>
          </dt>
          <dd>{blogEntity.publishDate ? <TextFormat value={blogEntity.publishDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="lucciadminApp.blog.content">Content</Translate>
            </span>
          </dt>
          <dd>{blogEntity.content}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.blog.titleImgUrl">Title Img Url</Translate>
          </dt>
          <dd>{blogEntity.titleImgUrl ? blogEntity.titleImgUrl.id : ''}</dd>
          <dt>
            <Translate contentKey="lucciadminApp.blog.relatedBlog">Related Blog</Translate>
          </dt>
          <dd>{blogEntity.relatedBlog ? blogEntity.relatedBlog.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blog" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blog/${blogEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ blog }: IRootState) => ({
  blogEntity: blog.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BlogDetail);
