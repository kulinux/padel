import {MATCH_FINISHED, ADD_TEAM} from './constants'


const initialState = {
  matches: [
    [
      { id: "matchA", team1: "team1", team2: "team2", finished: false, nextMatch: "matchE" },
      { id: "matchB", team1: "team3", team2: "team4", finished: false, nextMatch: "matchE" },
      { id: "matchC", team1: "team5", team2: "team6", finished: false, nextMatch: "matchF" },
      { id: "matchD", team1: "team7", team2: "team8", finished: false, nextMatch: "matchF" }
    ],
    [
      { id: "matchE", team1: "team1", team2: "team3", finished: false, nextMatch: "matchG" },
      { id: "matchF", team1: "team5", team2: "team7", finished: false, nextMatch: "matchG" }
    ],
    [
      { id: "matchG", team1: "team1", team2: "team5", finished: false }
    ]
  ],
  teams: [
    {id: "team1", name: "Team 1"},
    {id: "team2", name: "Team 2"},
    {id: "team3", name: "Team 3"},
    {id: "team4", name: "Team 4"},
    {id: "team5", name: "Team 5"},
    {id: "team6", name: "Team 6"},
    {id: "team7", name: "Team 7"},
    {id: "team8", name: "Team 8"},

  ]
}


const matchReducer = (state = {}, action) => {
  switch (action.type) {
    case MATCH_FINISHED:
        return {
          id: "matchA", team1: "team1", team2: "team2", finished: true, nextMatch: "matchE"
        };
      break;
    default:
      return state
  }
}

const tournmanentReducer = (state = initialState, action) => {
  switch (action.type) {
    case ADD_TEAM:
      return {
        ...state, teams: [...state.teams, action.payload]
      };
    case MATCH_FINISHED:
      return  {
        matches: state.matches.map(
          match => matchReducer(match, action)
        )
      }
      break;
    default:
      return state

  }
}

export default tournmanentReducer;
