import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './get-material.reducer';
import { IGetMaterial } from 'app/shared/model/get-material.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGetMaterialProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const GetMaterial = (props: IGetMaterialProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { getMaterialList, match, loading } = props;
  return (
    <div>
      <h2 id="get-material-heading">
        <Translate contentKey="lucciadminApp.getMaterial.home.title">Get Materials</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.getMaterial.home.createLabel">Create new Get Material</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {getMaterialList && getMaterialList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.getMaterial.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.getMaterial.material">Material</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.getMaterial.receptionist">Receptionist</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {getMaterialList.map((getMaterial, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${getMaterial.id}`} color="link" size="sm">
                      {getMaterial.id}
                    </Button>
                  </td>
                  <td>{getMaterial.date ? <TextFormat type="date" value={getMaterial.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{getMaterial.material ? <Link to={`material/${getMaterial.material.id}`}>{getMaterial.material.id}</Link> : ''}</td>
                  <td>
                    {getMaterial.receptionist ? (
                      <Link to={`receptionist/${getMaterial.receptionist.id}`}>{getMaterial.receptionist.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${getMaterial.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${getMaterial.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${getMaterial.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.getMaterial.home.notFound">No Get Materials found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ getMaterial }: IRootState) => ({
  getMaterialList: getMaterial.entities,
  loading: getMaterial.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GetMaterial);
