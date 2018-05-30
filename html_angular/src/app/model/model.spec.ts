import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Match, Team, Player , numberOfLevels, matchesOfLevel} from './model';

describe('Model', () => {

  let hs : Player = { id : "hs", name : "Han Solo"};
  let lo : Player = { id : "lo", name : "Leia Organa"};
  let cw : Player = { id : "cw", name : "Chewie"};
  let dv : Player = { id : "dv", name : "Lord Vader"};

  let t1 : Team = { player1 : 'hs', player2 : 'lo' };
  let t2 : Team = { player1 : 'cw', player2 : 'dv' };

  let match3: Match = { team1: t1, team2: t2, nextMatch: null};
  let match2: Match = { team1: t1, team2: t2, nextMatch: match3};
  let match1: Match = { team1: t1, team2: t2, nextMatch: match2};


  it('number of levels should works', () => {
    expect(numberOfLevels([match1])).toBe(3)
    expect(numberOfLevels([match2])).toBe(2)
    expect(numberOfLevels([match3])).toBe(1)
    expect(numberOfLevels(null)).toBe(0)
  });

  it('get matchesOfLevel should work', () => {
    expect(matchesOfLevel([match1], 0)).toEqual([match1])
    expect(matchesOfLevel([match1], 1)).toEqual([match2])
    expect(matchesOfLevel([match1], 2)).toEqual([match3])
  });

});
