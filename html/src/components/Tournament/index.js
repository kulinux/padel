import React from 'react';

import { connect } from "react-redux";


const Ronda = (matches) => (
  <div class="ronda">
  </div>
);

const mapStateToProps = state => {
  return { teams: state.teams };
};

export const ConnectedTeams = ({teams}) => (
    <div className="teams">
      <ul>
        { teams.map( team => <li key={team.id}>{team.name}</li> ) }
      </ul>
    </div>
);

export const Teams = connect(mapStateToProps)(ConnectedTeams);

export default Teams;
