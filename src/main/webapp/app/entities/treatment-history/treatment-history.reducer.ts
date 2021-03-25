import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITreatmentHistory, defaultValue } from 'app/shared/model/treatment-history.model';

export const ACTION_TYPES = {
  FETCH_TREATMENTHISTORY_LIST: 'treatmentHistory/FETCH_TREATMENTHISTORY_LIST',
  FETCH_TREATMENTHISTORY: 'treatmentHistory/FETCH_TREATMENTHISTORY',
  CREATE_TREATMENTHISTORY: 'treatmentHistory/CREATE_TREATMENTHISTORY',
  UPDATE_TREATMENTHISTORY: 'treatmentHistory/UPDATE_TREATMENTHISTORY',
  DELETE_TREATMENTHISTORY: 'treatmentHistory/DELETE_TREATMENTHISTORY',
  RESET: 'treatmentHistory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITreatmentHistory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TreatmentHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: TreatmentHistoryState = initialState, action): TreatmentHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TREATMENTHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_TREATMENTHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_TREATMENTHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_TREATMENTHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_TREATMENTHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_TREATMENTHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTHISTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TREATMENTHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_TREATMENTHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TREATMENTHISTORY):
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

const apiUrl = 'api/treatment-histories';

// Actions

export const getEntities: ICrudGetAllAction<ITreatmentHistory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TREATMENTHISTORY_LIST,
  payload: axios.get<ITreatmentHistory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITreatmentHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENTHISTORY,
    payload: axios.get<ITreatmentHistory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITreatmentHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TREATMENTHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITreatmentHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TREATMENTHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITreatmentHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TREATMENTHISTORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
