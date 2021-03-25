import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IReceptionist, defaultValue } from 'app/shared/model/receptionist.model';

export const ACTION_TYPES = {
  FETCH_RECEPTIONIST_LIST: 'receptionist/FETCH_RECEPTIONIST_LIST',
  FETCH_RECEPTIONIST: 'receptionist/FETCH_RECEPTIONIST',
  CREATE_RECEPTIONIST: 'receptionist/CREATE_RECEPTIONIST',
  UPDATE_RECEPTIONIST: 'receptionist/UPDATE_RECEPTIONIST',
  DELETE_RECEPTIONIST: 'receptionist/DELETE_RECEPTIONIST',
  RESET: 'receptionist/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IReceptionist>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ReceptionistState = Readonly<typeof initialState>;

// Reducer

export default (state: ReceptionistState = initialState, action): ReceptionistState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RECEPTIONIST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RECEPTIONIST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RECEPTIONIST):
    case REQUEST(ACTION_TYPES.UPDATE_RECEPTIONIST):
    case REQUEST(ACTION_TYPES.DELETE_RECEPTIONIST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RECEPTIONIST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RECEPTIONIST):
    case FAILURE(ACTION_TYPES.CREATE_RECEPTIONIST):
    case FAILURE(ACTION_TYPES.UPDATE_RECEPTIONIST):
    case FAILURE(ACTION_TYPES.DELETE_RECEPTIONIST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECEPTIONIST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECEPTIONIST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RECEPTIONIST):
    case SUCCESS(ACTION_TYPES.UPDATE_RECEPTIONIST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RECEPTIONIST):
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

const apiUrl = 'api/receptionists';

// Actions

export const getEntities: ICrudGetAllAction<IReceptionist> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RECEPTIONIST_LIST,
  payload: axios.get<IReceptionist>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IReceptionist> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RECEPTIONIST,
    payload: axios.get<IReceptionist>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IReceptionist> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RECEPTIONIST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IReceptionist> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RECEPTIONIST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IReceptionist> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RECEPTIONIST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
