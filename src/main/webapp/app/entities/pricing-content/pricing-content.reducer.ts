import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPricingContent, defaultValue } from 'app/shared/model/pricing-content.model';

export const ACTION_TYPES = {
  FETCH_PRICINGCONTENT_LIST: 'pricingContent/FETCH_PRICINGCONTENT_LIST',
  FETCH_PRICINGCONTENT: 'pricingContent/FETCH_PRICINGCONTENT',
  CREATE_PRICINGCONTENT: 'pricingContent/CREATE_PRICINGCONTENT',
  UPDATE_PRICINGCONTENT: 'pricingContent/UPDATE_PRICINGCONTENT',
  DELETE_PRICINGCONTENT: 'pricingContent/DELETE_PRICINGCONTENT',
  RESET: 'pricingContent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPricingContent>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PricingContentState = Readonly<typeof initialState>;

// Reducer

export default (state: PricingContentState = initialState, action): PricingContentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRICINGCONTENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRICINGCONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRICINGCONTENT):
    case REQUEST(ACTION_TYPES.UPDATE_PRICINGCONTENT):
    case REQUEST(ACTION_TYPES.DELETE_PRICINGCONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRICINGCONTENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRICINGCONTENT):
    case FAILURE(ACTION_TYPES.CREATE_PRICINGCONTENT):
    case FAILURE(ACTION_TYPES.UPDATE_PRICINGCONTENT):
    case FAILURE(ACTION_TYPES.DELETE_PRICINGCONTENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRICINGCONTENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRICINGCONTENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRICINGCONTENT):
    case SUCCESS(ACTION_TYPES.UPDATE_PRICINGCONTENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRICINGCONTENT):
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

const apiUrl = 'api/pricing-contents';

// Actions

export const getEntities: ICrudGetAllAction<IPricingContent> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRICINGCONTENT_LIST,
  payload: axios.get<IPricingContent>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPricingContent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRICINGCONTENT,
    payload: axios.get<IPricingContent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPricingContent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRICINGCONTENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPricingContent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRICINGCONTENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPricingContent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRICINGCONTENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
