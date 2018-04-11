import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {Match, Team, MatchResult} from "../../model/model"

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css']
})
export class MatchComponent implements OnInit {

  @Input() match : Match;

  @Output() finished = new EventEmitter<MatchResult>();

  finish: boolean = false;

  constructor() { }

  ngOnInit() {
  }

  onMouseClick(team: Team) {
    this.finish = true;

    let result = new MatchResult(this.match, team);

    this.finished.emit(result);
    /*
    if(this.match.nextMatch) {
      this.match.nextMatch.team1 = team;
    }
    */
  }

  onMouseOver(team: Team) {
    /*
    if(this.finish) return;
    if(this.match.nextMatch) {
      this.match.nextMatch.team1 = team;
    }
    */
  }

  onMouseLeave(team: Team) {
    /*
    if(this.finish) return;
    this.match.nextMatch.team1 = null;
    this.match.nextMatch.team2 = null;
    */
  }

}
