import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPricingCard, defaultValue } from 'app/shared/model/pricing-card.model';

export const ACTION_TYPES = {
  FETCH_PRICINGCARD_LIST: 'pricingCard/FETCH_PRICINGCARD_LIST',
  FETCH_PRICINGCARD: 'pricingCard/FETCH_PRICINGCARD',
  CREATE_PRICINGCARD: 'pricingCard/CREATE_PRICINGCARD',
  UPDATE_PRICINGCARD: 'pricingCard/UPDATE_PRICINGCARD',
  DELETE_PRICINGCARD: 'pricingCard/DELETE_PRICINGCARD',
  RESET: 'pricingCard/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPricingCard>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PricingCardState = Readonly<typeof initialState>;

// Reducer

export default (state: PricingCardState = initialState, action): PricingCardState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRICINGCARD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRICINGCARD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRICINGCARD):
    case REQUEST(ACTION_TYPES.UPDATE_PRICINGCARD):
    case REQUEST(ACTION_TYPES.DELETE_PRICINGCARD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRICINGCARD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRICINGCARD):
    case FAILURE(ACTION_TYPES.CREATE_PRICINGCARD):
    case FAILURE(ACTION_TYPES.UPDATE_PRICINGCARD):
    case FAILURE(ACTION_TYPES.DELETE_PRICINGCARD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRICINGCARD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRICINGCARD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRICINGCARD):
    case SUCCESS(ACTION_TYPES.UPDATE_PRICINGCARD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRICINGCARD):
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

const apiUrl = 'api/pricing-cards';

// Actions

export const getEntities: ICrudGetAllAction<IPricingCard> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRICINGCARD_LIST,
  payload: axios.get<IPricingCard>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPricingCard> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRICINGCARD,
    payload: axios.get<IPricingCard>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPricingCard> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRICINGCARD,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPricingCard> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRICINGCARD,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPricingCard> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRICINGCARD,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
