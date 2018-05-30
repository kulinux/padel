export class Player {
  id: string;
  name: string;
}
export class Team {
  name: string;
  player1: string;
  player2: string;
}
export class Match {
  team1: Team;
  team2: Team;
  nextMatch: Match;
}
export class Tournament {
    players: Array<Player> = [];
    matches: Array<Match> = [];
}

export class MatchResult {
  match: Match;
  winner: Team;

  constructor(match: Match, winner: Team) {
    this.match = match;
    this.winner = winner;
  }

}

export function matchesOfLevel(
  mtches: Array<Match>,
  level: number) : Array<Match>
{
  if(level == 0) return mtches;
  return matchesOfLevel(
    mtches
      .filter(mtch => mtch.nextMatch != null)
      .map(mtch => mtch.nextMatch),
    level - 1)
}

export function numberOfLevels(
  mtches: Array<Match>) : number {
    if(mtches == null || mtches.length == 0) return 0;

    let children = mtches
      .filter(mtch => mtch.nextMatch != null)
      .map(mtch => mtch.nextMatch)

    return 1 + numberOfLevels(children)
}
