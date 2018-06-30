import React from 'react';
import {Match} from '../Match';
import {connect} from 'react-redux';
import './styles.css';


const mapStateToProps = state => {
  return { matches: state.matches };
};

const MatchRow = ({matchRow, ronda, winMatch}) => (
  <div className="match-row col">
    <div className="text-center">Ronda {ronda + 1}</div>
    {
        matchRow.map( (match) => <Match match={match} winMatch={winMatch}/>)
    }
  </div>
)

export const ConnectedPanel = ({matches}) => (
  <div className="matches container-responsive">
    <div className="row">
       {
        matches.map( (match, i) => <MatchRow matchRow={match} ronda={i} winMatch={(match, team) => console.log(match, team)}/>)
    } </div>
  </div>
);


export const Panel = connect(mapStateToProps)(ConnectedPanel);

export default Panel;
