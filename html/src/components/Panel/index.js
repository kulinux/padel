import React from 'react';

export const Panel = ({rowMatches}) => (
  <div className="row-matches">
    {
      rowMatches =>
        rowMatches.map( (match) => <Match match={match}/>)
    }
  </div>
);
