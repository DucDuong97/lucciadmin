import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './img-url.reducer';
import { IImgUrl } from 'app/shared/model/img-url.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, IMAGE_FILE_SYSTEM_URL} from 'app/config/constants';

export interface IImgUrlProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ImgUrl = (props: IImgUrlProps) => {

  const [treatmentId, setTreatmentId] = useState(null);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const treatmentIdMaybe = params.get('treatmentId');
    setTreatmentId(treatmentIdMaybe);
    props.getEntities(treatmentIdMaybe);
  }, []);

  const { imgUrlList, match, loading } = props;
  return (
    <div>
      <h2 id="img-url-heading">
        <Translate contentKey="lucciadminApp.imgUrl.home.title">Img Urls</Translate>
        {treatmentId ? ` of Treatment ${treatmentId}` : ''}
        <Link to={`${match.url}/new${props.location.search}`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.imgUrl.home.createLabel">Create new Img Url</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {imgUrlList && imgUrlList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.imgUrl.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.imgUrl.path">Path</Translate>
                </th>
                <th>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {imgUrlList.map((imgUrl, i) => (
                <tr key={`entity-${i}`}>
                  <td>{imgUrl.id}</td>
                  <td>{imgUrl.name}</td>
                  <td>{imgUrl.path}</td>
                  <td>
                    <img src={`${IMAGE_FILE_SYSTEM_URL+imgUrl.imgUrl}`}
                         style={{maxWidth: 200, margin:20}} alt="hello world"/></td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${imgUrl.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.imgUrl.home.notFound">No Img Urls found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ imgUrl }: IRootState) => ({
  imgUrlList: imgUrl.entities,
  loading: imgUrl.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ImgUrl);
