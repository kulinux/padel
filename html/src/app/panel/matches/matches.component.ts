import { Component, OnInit, Input } from '@angular/core';
import {Match, matchesOfLevel, numberOfLevels} from '../../model/model'

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



  private computeAllMatches() {
    let res : Array<Array<Match>> = [];
    for(var i = 0; i < numberOfLevels(this.matches); i++) {
        res[i] = matchesOfLevel(this.matches, i);
    }
    return res;
  }



}
