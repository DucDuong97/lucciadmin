import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICustomerReview, defaultValue } from 'app/shared/model/customer-review.model';
import { upload } from 'app/entities/img-url/img-url.reducer';

export const ACTION_TYPES = {
  FETCH_CUSTOMERREVIEW_LIST: 'customerReview/FETCH_CUSTOMERREVIEW_LIST',
  FETCH_CUSTOMERREVIEW: 'customerReview/FETCH_CUSTOMERREVIEW',
  CREATE_CUSTOMERREVIEW: 'customerReview/CREATE_CUSTOMERREVIEW',
  UPDATE_CUSTOMERREVIEW: 'customerReview/UPDATE_CUSTOMERREVIEW',
  DELETE_CUSTOMERREVIEW: 'customerReview/DELETE_CUSTOMERREVIEW',
  RESET: 'customerReview/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICustomerReview>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CustomerReviewState = Readonly<typeof initialState>;

// Reducer

export default (state: CustomerReviewState = initialState, action): CustomerReviewState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CUSTOMERREVIEW_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CUSTOMERREVIEW):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CUSTOMERREVIEW):
    case REQUEST(ACTION_TYPES.UPDATE_CUSTOMERREVIEW):
    case REQUEST(ACTION_TYPES.DELETE_CUSTOMERREVIEW):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CUSTOMERREVIEW_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CUSTOMERREVIEW):
    case FAILURE(ACTION_TYPES.CREATE_CUSTOMERREVIEW):
    case FAILURE(ACTION_TYPES.UPDATE_CUSTOMERREVIEW):
    case FAILURE(ACTION_TYPES.DELETE_CUSTOMERREVIEW):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CUSTOMERREVIEW_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CUSTOMERREVIEW):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CUSTOMERREVIEW):
    case SUCCESS(ACTION_TYPES.UPDATE_CUSTOMERREVIEW):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CUSTOMERREVIEW):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/customer-reviews';

// Actions

export const getEntities: ICrudGetAllAction<ICustomerReview> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CUSTOMERREVIEW_LIST,
  payload: axios.get<ICustomerReview>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICustomerReview> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CUSTOMERREVIEW,
    payload: axios.get<ICustomerReview>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICustomerReview> = entity => async dispatch => {
  if (entity.file != null) {
    entity.customerImgUrl = (await upload(entity.file)).data;
  }
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CUSTOMERREVIEW,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICustomerReview> = entity => async dispatch => {
  if (entity.file != null) {
    entity.customerImgUrl = (await upload(entity.file)).data;
  }
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CUSTOMERREVIEW,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICustomerReview> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CUSTOMERREVIEW,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
