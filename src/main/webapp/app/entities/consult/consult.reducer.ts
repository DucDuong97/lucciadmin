import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConsult, defaultValue } from 'app/shared/model/consult.model';

export const ACTION_TYPES = {
  FETCH_CONSULT_LIST: 'consult/FETCH_CONSULT_LIST',
  FETCH_CONSULT: 'consult/FETCH_CONSULT',
  CREATE_CONSULT: 'consult/CREATE_CONSULT',
  UPDATE_CONSULT: 'consult/UPDATE_CONSULT',
  DELETE_CONSULT: 'consult/DELETE_CONSULT',
  RESET: 'consult/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConsult>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConsultState = Readonly<typeof initialState>;

// Reducer

export default (state: ConsultState = initialState, action): ConsultState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONSULT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONSULT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONSULT):
    case REQUEST(ACTION_TYPES.UPDATE_CONSULT):
    case REQUEST(ACTION_TYPES.DELETE_CONSULT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONSULT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONSULT):
    case FAILURE(ACTION_TYPES.CREATE_CONSULT):
    case FAILURE(ACTION_TYPES.UPDATE_CONSULT):
    case FAILURE(ACTION_TYPES.DELETE_CONSULT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONSULT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONSULT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONSULT):
    case SUCCESS(ACTION_TYPES.UPDATE_CONSULT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONSULT):
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

const apiUrl = 'api/consults';

// Actions

export const getEntities: ICrudGetAllAction<IConsult> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONSULT_LIST,
    payload: axios.get<IConsult>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConsult> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONSULT,
    payload: axios.get<IConsult>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConsult> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONSULT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConsult> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONSULT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConsult> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONSULT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const checkEntity: ICrudDeleteAction<IConsult> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}/check`;
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONSULT,
    payload: axios.post(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const cancelEntity: ICrudDeleteAction<IConsult> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}/cancel`;
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONSULT,
    payload: axios.post(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
