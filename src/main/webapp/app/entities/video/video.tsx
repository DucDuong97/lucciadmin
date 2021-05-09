import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './video.reducer';
import { IVideo } from 'app/shared/model/video.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVideoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Video = (props: IVideoProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { videoList, match, loading } = props;
  return (
    <div>
      <h2 id="video-heading">
        <Translate contentKey="lucciadminApp.video.home.title">Videos</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.video.home.createLabel">Create new Video</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {videoList && videoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.video.url">Url</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.video.serviceItem">Service Item</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {videoList.map((video, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${video.id}`} color="link" size="sm">
                      {video.id}
                    </Button>
                  </td>
                  <td>{video.url}</td>
                  <td>{video.serviceItem ? <Link to={`service-item/${video.serviceItem.id}`}>{video.serviceItem.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${video.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${video.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${video.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.video.home.notFound">No Videos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ video }: IRootState) => ({
  videoList: video.entities,
  loading: video.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Video);
