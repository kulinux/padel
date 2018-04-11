import { Component, OnInit, Input } from '@angular/core';
import {Match, MatchResult, matchesOfLevel, numberOfLevels} from '../../model/model'

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.css']
})
export class MatchesComponent implements OnInit {

  @Input() matches: Array<Match>;
  allMatches: Array<Array<Match>>;

  constructor() { }

  ngOnInit() {
    this.allMatches = this.computeAllMatches()
  }

  matchFinished(result: MatchResult) {
    if(result.match.nextMatch.team1 == undefined)
    {
      result.match.nextMatch.team1 = result.winner;
      return;
    }
    if(result.match.nextMatch.team2 == undefined)
    {
      result.match.nextMatch.team2 = result.winner;
      return;
    }
    console.log("Match Finished");
  }


  private computeAllMatches() {
    let res : Array<Array<Match>> = [];
    for(var i = 0; i < numberOfLevels(this.matches); i++) {
        res[i] = matchesOfLevel(this.matches, i);
    }
    return res;
  }



}
