import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMedicalRecord, defaultValue } from 'app/shared/model/medical-record.model';

export const ACTION_TYPES = {
  FETCH_MEDICALRECORD_LIST: 'medicalRecord/FETCH_MEDICALRECORD_LIST',
  FETCH_MEDICALRECORD: 'medicalRecord/FETCH_MEDICALRECORD',
  CREATE_MEDICALRECORD: 'medicalRecord/CREATE_MEDICALRECORD',
  UPDATE_MEDICALRECORD: 'medicalRecord/UPDATE_MEDICALRECORD',
  DELETE_MEDICALRECORD: 'medicalRecord/DELETE_MEDICALRECORD',
  RESET: 'medicalRecord/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMedicalRecord>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MedicalRecordState = Readonly<typeof initialState>;

// Reducer

export default (state: MedicalRecordState = initialState, action): MedicalRecordState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEDICALRECORD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDICALRECORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDICALRECORD):
    case REQUEST(ACTION_TYPES.UPDATE_MEDICALRECORD):
    case REQUEST(ACTION_TYPES.DELETE_MEDICALRECORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MEDICALRECORD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDICALRECORD):
    case FAILURE(ACTION_TYPES.CREATE_MEDICALRECORD):
    case FAILURE(ACTION_TYPES.UPDATE_MEDICALRECORD):
    case FAILURE(ACTION_TYPES.DELETE_MEDICALRECORD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICALRECORD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICALRECORD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDICALRECORD):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDICALRECORD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDICALRECORD):
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

const apiUrl = 'api/medical-records';

// Actions

export const getEntities: ICrudGetAllAction<IMedicalRecord> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MEDICALRECORD_LIST,
  payload: axios.get<IMedicalRecord>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMedicalRecord> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICALRECORD,
    payload: axios.get<IMedicalRecord>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMedicalRecord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDICALRECORD,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMedicalRecord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDICALRECORD,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMedicalRecord> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDICALRECORD,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
