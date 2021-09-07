import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBanner, defaultValue } from 'app/shared/model/banner.model';
import { upload } from 'app/entities/img-url/img-url.reducer';

export const ACTION_TYPES = {
  FETCH_BANNER_LIST: 'banner/FETCH_BANNER_LIST',
  FETCH_BANNER: 'banner/FETCH_BANNER',
  CREATE_BANNER: 'banner/CREATE_BANNER',
  UPDATE_BANNER: 'banner/UPDATE_BANNER',
  DELETE_BANNER: 'banner/DELETE_BANNER',
  RESET: 'banner/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBanner>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BannerState = Readonly<typeof initialState>;

// Reducer

export default (state: BannerState = initialState, action): BannerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BANNER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BANNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BANNER):
    case REQUEST(ACTION_TYPES.UPDATE_BANNER):
    case REQUEST(ACTION_TYPES.DELETE_BANNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BANNER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BANNER):
    case FAILURE(ACTION_TYPES.CREATE_BANNER):
    case FAILURE(ACTION_TYPES.UPDATE_BANNER):
    case FAILURE(ACTION_TYPES.DELETE_BANNER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANNER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANNER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BANNER):
    case SUCCESS(ACTION_TYPES.UPDATE_BANNER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BANNER):
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

const apiUrl = 'api/banners';

// Actions

export const getEntities: ICrudGetAllAction<IBanner> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BANNER_LIST,
  payload: axios.get<IBanner>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBanner> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BANNER,
    payload: axios.get<IBanner>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBanner> = entity => async dispatch => {
  if (entity.file != null) {
    const imgUrl = (await upload(entity.file)).data;
    entity.imgUrlId = imgUrl.id;
    entity.imgUrlName = imgUrl.name;
  }
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BANNER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBanner> = entity => async dispatch => {
  if (entity.file != null) {
    const imgUrl = (await upload(entity.file)).data;
    entity.imgUrlId = imgUrl.id;
    entity.imgUrlName = imgUrl.name;
  }
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BANNER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBanner> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BANNER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
