import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGetMaterial, defaultValue } from 'app/shared/model/get-material.model';

export const ACTION_TYPES = {
  FETCH_GETMATERIAL_LIST: 'getMaterial/FETCH_GETMATERIAL_LIST',
  FETCH_GETMATERIAL: 'getMaterial/FETCH_GETMATERIAL',
  CREATE_GETMATERIAL: 'getMaterial/CREATE_GETMATERIAL',
  UPDATE_GETMATERIAL: 'getMaterial/UPDATE_GETMATERIAL',
  DELETE_GETMATERIAL: 'getMaterial/DELETE_GETMATERIAL',
  RESET: 'getMaterial/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGetMaterial>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GetMaterialState = Readonly<typeof initialState>;

// Reducer

export default (state: GetMaterialState = initialState, action): GetMaterialState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GETMATERIAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GETMATERIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GETMATERIAL):
    case REQUEST(ACTION_TYPES.UPDATE_GETMATERIAL):
    case REQUEST(ACTION_TYPES.DELETE_GETMATERIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GETMATERIAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GETMATERIAL):
    case FAILURE(ACTION_TYPES.CREATE_GETMATERIAL):
    case FAILURE(ACTION_TYPES.UPDATE_GETMATERIAL):
    case FAILURE(ACTION_TYPES.DELETE_GETMATERIAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GETMATERIAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GETMATERIAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GETMATERIAL):
    case SUCCESS(ACTION_TYPES.UPDATE_GETMATERIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GETMATERIAL):
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

const apiUrl = 'api/get-materials';

// Actions

export const getEntities: ICrudGetAllAction<IGetMaterial> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GETMATERIAL_LIST,
  payload: axios.get<IGetMaterial>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGetMaterial> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GETMATERIAL,
    payload: axios.get<IGetMaterial>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGetMaterial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GETMATERIAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGetMaterial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GETMATERIAL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGetMaterial> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GETMATERIAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
