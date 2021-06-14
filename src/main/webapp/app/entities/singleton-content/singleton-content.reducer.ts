import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISingletonContent, defaultValue } from 'app/shared/model/singleton-content.model';
import { upload } from 'app/entities/img-url/img-url.reducer';

export const ACTION_TYPES = {
  FETCH_SINGLETONCONTENT_LIST: 'singletonContent/FETCH_SINGLETONCONTENT_LIST',
  FETCH_SINGLETONCONTENT: 'singletonContent/FETCH_SINGLETONCONTENT',
  CREATE_SINGLETONCONTENT: 'singletonContent/CREATE_SINGLETONCONTENT',
  UPDATE_SINGLETONCONTENT: 'singletonContent/UPDATE_SINGLETONCONTENT',
  DELETE_SINGLETONCONTENT: 'singletonContent/DELETE_SINGLETONCONTENT',
  RESET: 'singletonContent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISingletonContent>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SingletonContentState = Readonly<typeof initialState>;

// Reducer

export default (state: SingletonContentState = initialState, action): SingletonContentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SINGLETONCONTENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SINGLETONCONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SINGLETONCONTENT):
    case REQUEST(ACTION_TYPES.UPDATE_SINGLETONCONTENT):
    case REQUEST(ACTION_TYPES.DELETE_SINGLETONCONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SINGLETONCONTENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SINGLETONCONTENT):
    case FAILURE(ACTION_TYPES.CREATE_SINGLETONCONTENT):
    case FAILURE(ACTION_TYPES.UPDATE_SINGLETONCONTENT):
    case FAILURE(ACTION_TYPES.DELETE_SINGLETONCONTENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SINGLETONCONTENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SINGLETONCONTENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SINGLETONCONTENT):
    case SUCCESS(ACTION_TYPES.UPDATE_SINGLETONCONTENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SINGLETONCONTENT):
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

const apiUrl = 'api/singleton-contents';

// Actions

export const getEntities: ICrudGetAllAction<ISingletonContent> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SINGLETONCONTENT_LIST,
  payload: axios.get<ISingletonContent>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISingletonContent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SINGLETONCONTENT,
    payload: axios.get<ISingletonContent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISingletonContent> = entity => async dispatch => {
  if (entity.file != null) {
    entity.content = (await upload(entity.file)).data.imgUrl;
  }
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SINGLETONCONTENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISingletonContent> = entity => async dispatch => {
  if (entity.file != null) {
    entity.content = (await upload(entity.file)).data.imgUrl;
  }
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SINGLETONCONTENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISingletonContent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SINGLETONCONTENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
