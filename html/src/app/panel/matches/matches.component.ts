import { Component, OnInit, Input } from '@angular/core';
import { Match, MatchResult, matchesOfLevel, numberOfLevels } from '../../model/model';
import { RestService } from '../../services/rest.service';


import { Observable } from 'rxjs/Observable';
import { Subject }    from 'rxjs/Subject';
import { of }         from 'rxjs/observable/of';


@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.css']
})
export class MatchesComponent implements OnInit {

  @Input() matches: Array<Match>;
  allMatches: Array<Array<Match>>;

  constructor(private rest: RestService) { }

  ngOnInit() {
    this.allMatches = this.computeAllMatches()
  }

  matchFinished(result: MatchResult) {
    if(result.match.nextMatch.team1 == undefined)
    {
      this.rest.win(result)
        .subscribe( res => console.log(res) );
      result.match.nextMatch.team1 = result.winner;
      return;
    }
    if(result.match.nextMatch.team2 == undefined)
    {
      this.rest.win(result)
        .subscribe( res => console.log(res) );
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
