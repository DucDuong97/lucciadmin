import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPotential, defaultValue } from 'app/shared/model/potential.model';

export const ACTION_TYPES = {
  FETCH_POTENTIAL_LIST: 'potential/FETCH_POTENTIAL_LIST',
  FETCH_POTENTIAL: 'potential/FETCH_POTENTIAL',
  CREATE_POTENTIAL: 'potential/CREATE_POTENTIAL',
  UPDATE_POTENTIAL: 'potential/UPDATE_POTENTIAL',
  DELETE_POTENTIAL: 'potential/DELETE_POTENTIAL',
  RESET: 'potential/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPotential>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PotentialState = Readonly<typeof initialState>;

// Reducer

export default (state: PotentialState = initialState, action): PotentialState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POTENTIAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POTENTIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_POTENTIAL):
    case REQUEST(ACTION_TYPES.UPDATE_POTENTIAL):
    case REQUEST(ACTION_TYPES.DELETE_POTENTIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_POTENTIAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POTENTIAL):
    case FAILURE(ACTION_TYPES.CREATE_POTENTIAL):
    case FAILURE(ACTION_TYPES.UPDATE_POTENTIAL):
    case FAILURE(ACTION_TYPES.DELETE_POTENTIAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_POTENTIAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_POTENTIAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_POTENTIAL):
    case SUCCESS(ACTION_TYPES.UPDATE_POTENTIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_POTENTIAL):
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

const apiUrl = 'api/potentials';

// Actions

export const getEntities: ICrudGetAllAction<IPotential> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_POTENTIAL_LIST,
    payload: axios.get<IPotential>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPotential> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POTENTIAL,
    payload: axios.get<IPotential>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPotential> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POTENTIAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPotential> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POTENTIAL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPotential> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POTENTIAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
