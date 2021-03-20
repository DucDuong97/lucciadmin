import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceItem, defaultValue } from 'app/shared/model/service-item.model';

export const ACTION_TYPES = {
  FETCH_SERVICEITEM_LIST: 'serviceItem/FETCH_SERVICEITEM_LIST',
  FETCH_SERVICEITEM: 'serviceItem/FETCH_SERVICEITEM',
  CREATE_SERVICEITEM: 'serviceItem/CREATE_SERVICEITEM',
  UPDATE_SERVICEITEM: 'serviceItem/UPDATE_SERVICEITEM',
  DELETE_SERVICEITEM: 'serviceItem/DELETE_SERVICEITEM',
  RESET: 'serviceItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ServiceItemState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceItemState = initialState, action): ServiceItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEITEM):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEITEM):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEITEM):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEITEM):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEITEM):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEITEM):
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

const apiUrl = 'api/service-items';

// Actions

export const getEntities: ICrudGetAllAction<IServiceItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SERVICEITEM_LIST,
  payload: axios.get<IServiceItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IServiceItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEITEM,
    payload: axios.get<IServiceItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEITEM,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
