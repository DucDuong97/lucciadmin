import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './treatment.reducer';
import { ITreatment } from 'app/shared/model/treatment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ITreatmentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Treatment = (props: ITreatmentProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [planId, setPlanId] = useState(null);

  const getAllEntities = () => {
    const params = new URLSearchParams(props.location.search);
    const planIdMaybe = params.get('planId');
    setPlanId(planIdMaybe);
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`, planIdMaybe);
  };

  const sortEntities = () => {
    getAllEntities();
    // const params = new URLSearchParams(props.location.search);
    // const planIdMaybe = params.get('planId');
    // const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}${planIdMaybe ? `&planId=${planIdMaybe}` : ''}`;
    // if (props.location.search !== endURL) {
    //   props.history.push(`${props.location.pathname}${endURL}`);
    // }
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

  const { treatmentList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="treatment-heading">
        <Translate contentKey="lucciadminApp.treatment.home.title">Treatments</Translate>
        {planId ? ` c???a H??? s?? ${planId}` : ''}
      </h2>
      <div className="table-responsive">
        {treatmentList && treatmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="lucciadminApp.treatment.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('date')}>
                  <Translate contentKey="lucciadminApp.treatment.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nextPlan')}>
                  <Translate contentKey="lucciadminApp.treatment.nextPlan">Next Plan</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('revisitDate')}>
                  <Translate contentKey="lucciadminApp.treatment.revisitDate">Revisit Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatment.doctor">Doctor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatment.treatmentPlan">Treatment Plan</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {treatmentList.map((treatment, i) => (
                <tr key={`entity-${i}`}>
                  <td>{treatment.id}</td>
                  <td>{treatment.description}</td>
                  <td>{treatment.date ? <TextFormat type="date" value={treatment.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{treatment.nextPlan}</td>
                  <td>
                    {treatment.revisitDate ? <TextFormat type="date" value={treatment.revisitDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{treatment.doctorName ? <Link to={`employee/${treatment.doctorId}`}>{treatment.doctorName}</Link> : ''}</td>
                  <td>
                    {treatment.treatmentPlanId ? (
                      <Link to={`treatment-plan/${treatment.treatmentPlanId}`}>{treatment.treatmentPlanId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      {props.viewPermission &&
                      <Button tag={Link} to={`${match.url}/${treatment.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      }
                      {props.editPermission &&
                      <Button
                        tag={Link}
                        to={`${match.url}/${treatment.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${treatment.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="lucciadminApp.treatment.home.notFound">No Treatments found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={treatmentList && treatmentList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ treatment, authentication }: IRootState) => ({
  treatmentList: treatment.entities,
  loading: treatment.loading,
  totalItems: treatment.totalItems,

  createPermission: false,
  viewPermission: authentication.isOperationsDirector || authentication.isDoctor,
  editPermission: authentication.isOperationsDirector || authentication.isDoctor,
  deletePermission: false,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Treatment);
