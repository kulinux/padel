import React from 'react';


const Team = ({name}) => (
    <div class="team">
        Team {name}
    </div>
);


const Match =  (props) => (
  <div class="match">
    <Team name="team uno"/>
    <Team name="team dos"/>
  </div>
);

export default Match;
