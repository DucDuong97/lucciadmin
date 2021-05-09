import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVideo, defaultValue } from 'app/shared/model/video.model';

export const ACTION_TYPES = {
  FETCH_VIDEO_LIST: 'video/FETCH_VIDEO_LIST',
  FETCH_VIDEO: 'video/FETCH_VIDEO',
  CREATE_VIDEO: 'video/CREATE_VIDEO',
  UPDATE_VIDEO: 'video/UPDATE_VIDEO',
  DELETE_VIDEO: 'video/DELETE_VIDEO',
  RESET: 'video/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVideo>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type VideoState = Readonly<typeof initialState>;

// Reducer

export default (state: VideoState = initialState, action): VideoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VIDEO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VIDEO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VIDEO):
    case REQUEST(ACTION_TYPES.UPDATE_VIDEO):
    case REQUEST(ACTION_TYPES.DELETE_VIDEO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_VIDEO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VIDEO):
    case FAILURE(ACTION_TYPES.CREATE_VIDEO):
    case FAILURE(ACTION_TYPES.UPDATE_VIDEO):
    case FAILURE(ACTION_TYPES.DELETE_VIDEO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VIDEO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VIDEO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VIDEO):
    case SUCCESS(ACTION_TYPES.UPDATE_VIDEO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VIDEO):
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

const apiUrl = 'api/videos';

// Actions

export const getEntities: ICrudGetAllAction<IVideo> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VIDEO_LIST,
  payload: axios.get<IVideo>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IVideo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VIDEO,
    payload: axios.get<IVideo>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVideo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VIDEO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVideo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VIDEO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVideo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VIDEO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
