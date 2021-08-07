import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './booking.reducer';
import { IBooking } from 'app/shared/model/booking.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, APP_TIME_FORMAT, AUTHORITIES} from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IBookingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Booking = (props: IBookingProps) => {
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

  const { bookingList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="booking-heading">
        <Translate contentKey="lucciadminApp.booking.home.title">Bookings</Translate>
        {props.createPermission &&
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lucciadminApp.booking.home.createLabel">Create new Booking</Translate>
          </Link>
        }
      </h2>
      <div className="table-responsive">
        {bookingList && bookingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('time')}>
                  <Translate contentKey="lucciadminApp.booking.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('time')}>
                  <Translate contentKey="lucciadminApp.booking.time">Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="lucciadminApp.booking.correspondDoctor">Correspond Doctor</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                {props.viewCustomerPermission &&
                <th>
                  <Translate contentKey="lucciadminApp.booking.customer">Customer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                }
                {props.viewPlanPermission &&
                <th>
                  <Translate contentKey="lucciadminApp.booking.treatmentPlan">Treatment Plan</Translate>
                  <FontAwesomeIcon icon="sort"/>
                </th>
                }
                <th>
                  <Translate contentKey="lucciadminApp.booking.branch">Branch</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bookingList.map((booking, i) => (
                <tr key={`entity-${i}`}>
                  <td>{booking.id}</td>
                  <td>{booking.date ? <TextFormat type="date" value={booking.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{booking.time ? <TextFormat type="number" value={booking.time} format={APP_TIME_FORMAT} /> : null}</td>
                  <td>
                    {booking.correspondDoctorId ? (
                      <Link to={`employee/${booking.correspondDoctorId}`}>{booking.correspondDoctorId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  {props.viewCustomerPermission &&
                  <td>{booking.customerId ? <Link to={`customer/${booking.customerId}`}>{booking.customerId}</Link> : ''}</td>
                  }
                  {props.viewPlanPermission &&
                  <td>
                    {booking.treatmentPlanId ?
                      <Link to={`treatment-plan/${booking.treatmentPlanId}`}>{booking.treatmentPlanId}</Link> : ''}
                  </td>
                  }
                  <td>{booking.branchId ? <Link to={`branch/${booking.branchId}`}>{booking.branchId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      {/*<Button tag={Link} to={`${match.url}/${booking.id}`} color="info" size="sm">*/}
                      {/*  <FontAwesomeIcon icon="eye" />{' '}*/}
                      {/*  <span className="d-none d-md-inline">*/}
                      {/*    <Translate contentKey="entity.action.view">View</Translate>*/}
                      {/*  </span>*/}
                      {/*</Button>*/}

                      {props.cameConfirmPermission && booking.correspondDoctorId && booking.time !== '00:00:00' &&
                      <Button
                        tag={Link}
                        to={`${match.url}/${booking.id}/check-confirm?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${booking.id}/cancel-confirm?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                      {props.updatePermission &&
                        <Button
                          tag={Link}
                          to={`${match.url}/${booking.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color={booking.time !== '00:00:00' ? 'warning' : 'primary'}
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
                          to={`${match.url}/${booking.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="lucciadminApp.booking.home.notFound">No Bookings found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={bookingList && bookingList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ booking, authentication }: IRootState) => ({
  bookingList: booking.entities,
  loading: booking.loading,
  totalItems: booking.totalItems,

  viewCustomerPermission: authentication.isReceptionist,
  viewPlanPermission: authentication.isOperationsDirector || authentication.isDoctor,
  createPermission: false,
  cameConfirmPermission: authentication.isReceptionist,
  notCameConfirmPermission: authentication.isReceptionist,
  updatePermission: authentication.isReceptionist,
  deletePermission: false,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Booking);
