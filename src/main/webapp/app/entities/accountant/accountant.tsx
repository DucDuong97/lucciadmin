import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './accountant.reducer';
import { IAccountant } from 'app/shared/model/accountant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAccountantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Accountant = (props: IAccountantProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { accountantList, match, loading } = props;
  return (
    <div>
      <h2 id="accountant-heading">
        <Translate contentKey="lucciadminApp.accountant.home.title">Accountants</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lucciadminApp.accountant.home.createLabel">Create new Accountant</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {accountantList && accountantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.accountant.salary">Salary</Translate>
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.accountant.person">Person</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {accountantList.map((accountant, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${accountant.id}`} color="link" size="sm">
                      {accountant.id}
                    </Button>
                  </td>
                  <td>{accountant.salary}</td>
                  <td>{accountant.person ? <Link to={`person/${accountant.person.id}`}>{accountant.person.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${accountant.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${accountant.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${accountant.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="lucciadminApp.accountant.home.notFound">No Accountants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ accountant }: IRootState) => ({
  accountantList: accountant.entities,
  loading: accountant.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Accountant);
