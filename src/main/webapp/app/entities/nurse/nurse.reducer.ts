import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INurse, defaultValue } from 'app/shared/model/nurse.model';

export const ACTION_TYPES = {
  FETCH_NURSE_LIST: 'nurse/FETCH_NURSE_LIST',
  FETCH_NURSE: 'nurse/FETCH_NURSE',
  CREATE_NURSE: 'nurse/CREATE_NURSE',
  UPDATE_NURSE: 'nurse/UPDATE_NURSE',
  DELETE_NURSE: 'nurse/DELETE_NURSE',
  RESET: 'nurse/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INurse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type NurseState = Readonly<typeof initialState>;

// Reducer

export default (state: NurseState = initialState, action): NurseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NURSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NURSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_NURSE):
    case REQUEST(ACTION_TYPES.UPDATE_NURSE):
    case REQUEST(ACTION_TYPES.DELETE_NURSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_NURSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NURSE):
    case FAILURE(ACTION_TYPES.CREATE_NURSE):
    case FAILURE(ACTION_TYPES.UPDATE_NURSE):
    case FAILURE(ACTION_TYPES.DELETE_NURSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_NURSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_NURSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_NURSE):
    case SUCCESS(ACTION_TYPES.UPDATE_NURSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_NURSE):
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

const apiUrl = 'api/nurses';

// Actions

export const getEntities: ICrudGetAllAction<INurse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NURSE_LIST,
  payload: axios.get<INurse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<INurse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NURSE,
    payload: axios.get<INurse>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<INurse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NURSE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INurse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NURSE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<INurse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NURSE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
