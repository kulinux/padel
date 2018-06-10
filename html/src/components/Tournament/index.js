import React from 'react';
import { connect } from 'react-redux';

import {AddTeamForm} from '../AddTeamForm'


const Ronda = (matches) => (
  <div class="ronda">
  </div>
);

const mapStateToProps = state => {
  return { teams: state.teams };
};


export const ConnectedTeams = ({teams}) => (
    <div>
      <AddTeamForm key="addTeamForm"/>
      <div key="teams" className="teams">
        <ul>
          { teams.map( team => console.log(team) ) }
          { teams.map( team => <li key={team.id}>{team.name}</li> ) }
        </ul>
      </div>
    </div>
);

export const Teams = connect(mapStateToProps)(ConnectedTeams);

export default Teams;
