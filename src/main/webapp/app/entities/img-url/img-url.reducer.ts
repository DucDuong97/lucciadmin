import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IImgUrl, defaultValue } from 'app/shared/model/img-url.model';

export const ACTION_TYPES = {
  FETCH_IMGURL_LIST: 'imgUrl/FETCH_IMGURL_LIST',
  FETCH_IMGURL: 'imgUrl/FETCH_IMGURL',
  CREATE_IMGURL: 'imgUrl/CREATE_IMGURL',
  UPDATE_IMGURL: 'imgUrl/UPDATE_IMGURL',
  DELETE_IMGURL: 'imgUrl/DELETE_IMGURL',
  RESET: 'imgUrl/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IImgUrl>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ImgUrlState = Readonly<typeof initialState>;

// Reducer

export default (state: ImgUrlState = initialState, action): ImgUrlState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_IMGURL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_IMGURL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_IMGURL):
    case REQUEST(ACTION_TYPES.UPDATE_IMGURL):
    case REQUEST(ACTION_TYPES.DELETE_IMGURL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_IMGURL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_IMGURL):
    case FAILURE(ACTION_TYPES.CREATE_IMGURL):
    case FAILURE(ACTION_TYPES.UPDATE_IMGURL):
    case FAILURE(ACTION_TYPES.DELETE_IMGURL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_IMGURL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_IMGURL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_IMGURL):
    case SUCCESS(ACTION_TYPES.UPDATE_IMGURL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_IMGURL):
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

const apiUrl = 'api/img-urls';

// Actions

export const getEntities: ICrudGetAllAction<IImgUrl> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_IMGURL_LIST,
  payload: axios.get<IImgUrl>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IImgUrl> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_IMGURL,
    payload: axios.get<IImgUrl>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IImgUrl> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_IMGURL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const uploadImage = (imageFile: File | Blob) => async dispatch => {
  const formData = new FormData();
  formData.append('image', imageFile);
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_IMGURL,
    payload:
      axios.post('api/img-urls/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IImgUrl> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_IMGURL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IImgUrl> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_IMGURL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
