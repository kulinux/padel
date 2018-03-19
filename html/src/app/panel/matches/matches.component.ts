import { Component, OnInit, Input } from '@angular/core';
import {Match, matchesOfLevel, numberOfLevels} from '../../model/model'

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.css']
})
export class MatchesComponent implements OnInit {

  @Input() matches: Array<Match>;

  constructor() { }

  ngOnInit() {
  }

  firstLevel() {
    return this.matches;
  }

  matchesOfLevel(level: number) {
    return matchesOfLevel(this.matches, level)
  }

  numberOfLevels() {
    return numberOfLevels(this.matches, 0)
  }


}
