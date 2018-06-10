import { createStore, combineReducers, applyMiddleware} from 'redux';

import {teamsReducer} from './components/Teams/reducer'

const logger = store => next => action => {
  console.log('action invoked')
}

const store = createStore( teamsReducer )

export default store
