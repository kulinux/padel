import { Component, OnInit, Input } from '@angular/core';
import {Match, Team} from "../../model/model"

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css']
})
export class MatchComponent implements OnInit {

  @Input() match : Match;

  constructor() { }

  ngOnInit() {
  }

  onMouseOver(team: Team) {
    console.log("mouse over!!" + team.name)
    this.match.nextMatch.team1 = team;
  }

  onMouseLeave(team: Team) {
    console.log("mouse leave!!")
  }

}
