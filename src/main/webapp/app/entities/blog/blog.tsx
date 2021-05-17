import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './blog.reducer';
import { IBlog } from 'app/shared/model/blog.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBlogProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Blog = (props: IBlogProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { blogList, match, loading } = props;
  return (
    <div>
      <h2 id="blog-heading">
        <Translate contentKey="lucciadminApp.blog.home.title">Blogs</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.blog.home.createLabel">Create new Blog</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {blogList && blogList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.blog.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.blog.publishDate">Publish Date</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.blog.content">Content</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.blog.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.blog.titleImgUrl">Title Img Url</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.blog.serviceItem">Service Item</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {blogList.map((blog, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${blog.id}`} color="link" size="sm">
                      {blog.id}
                    </Button>
                  </td>
                  <td>{blog.title}</td>
                  <td>{blog.publishDate ? <TextFormat type="date" value={blog.publishDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{blog.content}</td>
                  <td>{blog.description}</td>
                  <td>{blog.titleImgUrl ? <Link to={`img-url/${blog.titleImgUrl.id}`}>{blog.titleImgUrl.id}</Link> : ''}</td>
                  <td>{blog.serviceItem ? <Link to={`service-item/${blog.serviceItem.id}`}>{blog.serviceItem.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${blog.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${blog.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${blog.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="lucciadminApp.blog.home.notFound">No Blogs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ blog }: IRootState) => ({
  blogList: blog.entities,
  loading: blog.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Blog);
