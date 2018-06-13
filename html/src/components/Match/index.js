import React from 'react';
import './styles.css'


const Team = ({name}) => (
    <div className="team">
        {name}
    </div>
);


export const Match =  (props) => (
  <div className="match">
    <Team name="team uno"/>
    <Team name="team dos"/>
  </div>
);

export default Match;
