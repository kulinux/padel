import { createStore, combineReducers, applyMiddleware} from 'redux';

import tournmanentReducer from './components/Tournament/reducer'

const logger = store => next => action => {
  console.log('action invoked')
}

const store = createStore( tournmanentReducer )

export default store
