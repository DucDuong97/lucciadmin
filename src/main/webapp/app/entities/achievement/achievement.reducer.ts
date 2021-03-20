import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAchievement, defaultValue } from 'app/shared/model/achievement.model';

export const ACTION_TYPES = {
  FETCH_ACHIEVEMENT_LIST: 'achievement/FETCH_ACHIEVEMENT_LIST',
  FETCH_ACHIEVEMENT: 'achievement/FETCH_ACHIEVEMENT',
  CREATE_ACHIEVEMENT: 'achievement/CREATE_ACHIEVEMENT',
  UPDATE_ACHIEVEMENT: 'achievement/UPDATE_ACHIEVEMENT',
  DELETE_ACHIEVEMENT: 'achievement/DELETE_ACHIEVEMENT',
  RESET: 'achievement/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAchievement>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AchievementState = Readonly<typeof initialState>;

// Reducer

export default (state: AchievementState = initialState, action): AchievementState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACHIEVEMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACHIEVEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ACHIEVEMENT):
    case REQUEST(ACTION_TYPES.UPDATE_ACHIEVEMENT):
    case REQUEST(ACTION_TYPES.DELETE_ACHIEVEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ACHIEVEMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACHIEVEMENT):
    case FAILURE(ACTION_TYPES.CREATE_ACHIEVEMENT):
    case FAILURE(ACTION_TYPES.UPDATE_ACHIEVEMENT):
    case FAILURE(ACTION_TYPES.DELETE_ACHIEVEMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACHIEVEMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACHIEVEMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACHIEVEMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ACHIEVEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACHIEVEMENT):
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

const apiUrl = 'api/achievements';

// Actions

export const getEntities: ICrudGetAllAction<IAchievement> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ACHIEVEMENT_LIST,
  payload: axios.get<IAchievement>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAchievement> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACHIEVEMENT,
    payload: axios.get<IAchievement>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAchievement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACHIEVEMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAchievement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACHIEVEMENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAchievement> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACHIEVEMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
