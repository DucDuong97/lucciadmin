import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './consult.reducer';
import { IConsult } from 'app/shared/model/consult.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IConsultProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Consult = (props: IConsultProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { consultList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="consult-heading">
        <Translate contentKey="lucciadminApp.consult.home.title">Consults</Translate>
        {props.createPermission &&
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lucciadminApp.consult.home.createLabel">Create new Consult</Translate>
          </Link>
        }
      </h2>
      <div className="table-responsive">
        {consultList && consultList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('time')}>
                  <Translate contentKey="lucciadminApp.consult.time">Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('note')}>
                  <Translate contentKey="lucciadminApp.consult.note">Note</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.consult.customer">Customer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.consult.branch">Branch</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.consult.consultingDoctor">Consulting Doctor</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {consultList.map((consult, i) => (
                <tr key={`entity-${i}`}>
                  <td>{consult.id}</td>
                  <td>{consult.time ? <TextFormat type="date" value={consult.time} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{consult.note}</td>
                  <td>{consult.customerName ? <Link to={`customer/${consult.customerId}`}>{consult.customerName}</Link> : ''}</td>
                  <td>{consult.branchAdress ? <Link to={`branch/${consult.branchId}`}>{consult.branchAdress}</Link> : ''}</td>
                  <td>
                    {consult.consultingDoctorName ? (
                      <Link to={`employee/${consult.consultingDoctorId}`}>{consult.consultingDoctorName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      {props.cameConfirmPermission && consult.services.length > 0 &&
                      <Button
                        tag={Link}
                        to={`${match.url}/${consult.id}/check-confirm?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="success"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="check" />{' '}
                        <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.check">Check</Translate>
                          </span>
                      </Button>
                      }

                      {props.notCameConfirmPermission &&
                      <Button
                        tag={Link}
                        to={`${match.url}/${consult.id}/cancel-confirm?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="times" />{' '}
                        <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.cancel">Cancel</Translate>
                          </span>
                      </Button>
                      }
                      &nbsp;
                      {props.viewPermission &&
                      <Button tag={Link} to={`${match.url}/${consult.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye"/>{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      }
                      {props.editPermission &&
                      <Button
                        tag={Link}
                        to={`${match.url}/${consult.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      }
                      {props.deletePermission &&
                      <Button
                        tag={Link}
                        to={`${match.url}/${consult.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                      }
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="lucciadminApp.consult.home.notFound">No Consults found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={consultList && consultList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ consult, authentication }: IRootState) => ({
  consultList: consult.entities,
  loading: consult.loading,
  totalItems: consult.totalItems,

  cameConfirmPermission: authentication.isConsultant,
  notCameConfirmPermission: authentication.isConsultant,
  createPermission: authentication.isConsultant,
  viewPermission:   authentication.isConsultant,
  editPermission:   authentication.isConsultant || authentication.isDoctor,
  deletePermission: false,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Consult);
