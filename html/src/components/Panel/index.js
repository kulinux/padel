import React from 'react';
import {Match} from '../Match';
import {connect} from 'react-redux';


const mapStateToProps = state => {
  return { matches: state.matches };
};

const MatchRow = ({matchRow}) => (
  <div className="match-row">
    MatchRow
    {
        matchRow.map( (match) => <Match match={match}/>)
    }
  </div>
)

export const ConnectedPanel = ({matches}) => (
  <div className="matches">
    {
        matches.map( (match) => <MatchRow matchRow={match}/>)
    }
  </div>
);


export const Panel = connect(mapStateToProps)(ConnectedPanel);

export default Panel;
