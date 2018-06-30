import React from 'react';
import './styles.css'


const Team = ({name, win}) => (
    <div className="team">
        {name}
        <button className="btn btn-sm btn-outline-secondary" type="submit" onClick={ev => win(name)}>
          <span className="fas fa-trophy" aria-hidden="true"></span>
        </button>
    </div>
);


export const Match =  ({match, winMatch}) => (
  <div className="match">
    <Team name={match.team1} win={team => winMatch(match, team) }/>
    <Team name={match.team2} win={team => winMatch(match, team) }/>
  </div>
);

export default Match;
