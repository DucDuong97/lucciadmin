import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAccountant, defaultValue } from 'app/shared/model/accountant.model';

export const ACTION_TYPES = {
  FETCH_ACCOUNTANT_LIST: 'accountant/FETCH_ACCOUNTANT_LIST',
  FETCH_ACCOUNTANT: 'accountant/FETCH_ACCOUNTANT',
  CREATE_ACCOUNTANT: 'accountant/CREATE_ACCOUNTANT',
  UPDATE_ACCOUNTANT: 'accountant/UPDATE_ACCOUNTANT',
  DELETE_ACCOUNTANT: 'accountant/DELETE_ACCOUNTANT',
  RESET: 'accountant/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAccountant>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AccountantState = Readonly<typeof initialState>;

// Reducer

export default (state: AccountantState = initialState, action): AccountantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ACCOUNTANT):
    case REQUEST(ACTION_TYPES.UPDATE_ACCOUNTANT):
    case REQUEST(ACTION_TYPES.DELETE_ACCOUNTANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTANT):
    case FAILURE(ACTION_TYPES.CREATE_ACCOUNTANT):
    case FAILURE(ACTION_TYPES.UPDATE_ACCOUNTANT):
    case FAILURE(ACTION_TYPES.DELETE_ACCOUNTANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTANT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACCOUNTANT):
    case SUCCESS(ACTION_TYPES.UPDATE_ACCOUNTANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACCOUNTANT):
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

const apiUrl = 'api/accountants';

// Actions

export const getEntities: ICrudGetAllAction<IAccountant> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ACCOUNTANT_LIST,
  payload: axios.get<IAccountant>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAccountant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTANT,
    payload: axios.get<IAccountant>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAccountant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACCOUNTANT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAccountant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACCOUNTANT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAccountant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACCOUNTANT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
