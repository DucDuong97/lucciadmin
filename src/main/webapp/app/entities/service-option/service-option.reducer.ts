import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceOption, defaultValue } from 'app/shared/model/service-option.model';

export const ACTION_TYPES = {
  FETCH_SERVICEOPTION_LIST: 'serviceOption/FETCH_SERVICEOPTION_LIST',
  FETCH_SERVICEOPTION: 'serviceOption/FETCH_SERVICEOPTION',
  CREATE_SERVICEOPTION: 'serviceOption/CREATE_SERVICEOPTION',
  UPDATE_SERVICEOPTION: 'serviceOption/UPDATE_SERVICEOPTION',
  DELETE_SERVICEOPTION: 'serviceOption/DELETE_SERVICEOPTION',
  RESET: 'serviceOption/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceOption>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ServiceOptionState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceOptionState = initialState, action): ServiceOptionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEOPTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEOPTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEOPTION):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEOPTION):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEOPTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEOPTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEOPTION):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEOPTION):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEOPTION):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEOPTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEOPTION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEOPTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEOPTION):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEOPTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEOPTION):
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

const apiUrl = 'api/service-options';

// Actions

export const getEntities: ICrudGetAllAction<IServiceOption> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SERVICEOPTION_LIST,
  payload: axios.get<IServiceOption>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IServiceOption> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEOPTION,
    payload: axios.get<IServiceOption>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceOption> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEOPTION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceOption> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEOPTION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceOption> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEOPTION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
