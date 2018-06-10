import {MATCH_FINISHED} from './constants'
import {ADD_TEAM} from './constants'

export const addTeam = (arg) => ({type: ADD_TEAM, payload: {id: arg.id, name: arg.name}})
export const winMatch = match => ({type: MATCH_FINISHED, payload: {}})
