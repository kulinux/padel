import {MATCH_FINISHED, ADD_TEAM} from './constants'

export const winMatch = match => ({type: MATCH_FINISHED, payload: {}})
export const addTeam = matchName => ({type: ADD_TEAM, payload: {name: matchName}})
