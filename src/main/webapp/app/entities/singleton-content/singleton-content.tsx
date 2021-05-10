import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './singleton-content.reducer';
import { ISingletonContent } from 'app/shared/model/singleton-content.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISingletonContentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SingletonContent = (props: ISingletonContentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { singletonContentList, match, loading } = props;
  return (
    <div>
      <h2 id="singleton-content-heading">
        <Translate contentKey="lucciadminApp.singletonContent.home.title">Singleton Contents</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.singletonContent.home.createLabel">Create new Singleton Content</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {singletonContentList && singletonContentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lucciadminApp.singletonContent.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.singletonContent.content">Content</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {singletonContentList.map((singletonContent, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Translate contentKey={`lucciadminApp.ContentType.${singletonContent.type}`} />
                  </td>
                  <td>{singletonContent.content}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${singletonContent.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${singletonContent.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${singletonContent.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.singletonContent.home.notFound">No Singleton Contents found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ singletonContent }: IRootState) => ({
  singletonContentList: singletonContent.entities,
  loading: singletonContent.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SingletonContent);
