import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './treatment-plan.reducer';
import { ITreatmentPlan } from 'app/shared/model/treatment-plan.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ITreatmentPlanProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const TreatmentPlan = (props: ITreatmentPlanProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [customerId, setCustomerId] = useState(null);

  const getAllEntities = () => {
    const params = new URLSearchParams(props.location.search);
    const customerIdMaybe = params.get('customerId');
    setCustomerId(customerIdMaybe);
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage,
      `${paginationState.sort},${paginationState.order}`, customerIdMaybe);
  };

  const sortEntities = () => {
    getAllEntities();
    // const params = new URLSearchParams(props.location.search);
    // const customerIdMaybe = params.get('customerId');
    // const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}${customerIdMaybe ? `&customerId=${customerIdMaybe}` : ''}`;
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

  const { treatmentPlanList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="treatment-plan-heading">
        <Translate contentKey="lucciadminApp.treatmentPlan.home.title">Treatment Plans</Translate> {customerId ? ` of Customer ${customerId}` : ''}
      </h2>
      <div className="table-responsive">
        {treatmentPlanList && treatmentPlanList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.treatmentPlan.service">Service</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {treatmentPlanList.map((treatmentPlan, i) => (
                <tr key={`entity-${i}`}>
                  <td>{treatmentPlan.id}</td>
                  <td>
                    {treatmentPlan.serviceName ? (
                      <Link to={`pricing-card/${treatmentPlan.serviceId}`}>{treatmentPlan.serviceName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      {props.createTreatmentPermission &&
                      <Button tag={Link} to={`/treatment/new?planId=${treatmentPlan.id}`} color="link" size="sm">
                        <FontAwesomeIcon icon="plus"/>{' '}
                        <span className="d-none d-md-inline">
                            <Translate contentKey="lucciadminApp.treatmentPlan.createTreatment">Create Treatment</Translate>
                          </span>
                      </Button>
                      }
                      {props.viewTreatmentsPermission &&
                      <Button tag={Link} to={`/treatment?planId=${treatmentPlan.id}`} color="warning" size="sm">
                        <FontAwesomeIcon icon="eye"/>{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="lucciadminApp.treatmentPlan.viewTreatments">View all Treatments</Translate>
                        </span>
                      </Button>
                      }
                      {props.viewPermission &&
                      <Button tag={Link} to={`${match.url}/${treatmentPlan.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      }
                      {props.editPermission &&
                      <Button
                        tag={Link}
                        to={`${match.url}/${treatmentPlan.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${treatmentPlan.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash"/>{' '}
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
              <Translate contentKey="lucciadminApp.treatmentPlan.home.notFound">No Treatment Plans found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={treatmentPlanList && treatmentPlanList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ treatmentPlan, authentication }: IRootState) => ({
  treatmentPlanList: treatmentPlan.entities,
  loading: treatmentPlan.loading,
  totalItems: treatmentPlan.totalItems,

  createTreatmentPermission: authentication.isReceptionist,
  viewTreatmentsPermission: authentication.isDoctor,
  // createPermission: authentication.isReceptionist,
  viewPermission: authentication.isDoctor,
  editPermission: authentication.isDoctor,
  deletePermission: false,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentPlan);
