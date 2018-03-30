import { Component, OnInit } from '@angular/core';
import {Tournament} from '../../model/model'

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})


export class PanelComponent implements OnInit {

  tournament: Tournament = {
    players : [
      { id: "hs", name : 'Han Solo' },
      { id: "lo", name : 'Leia Organa' } ],
    matches: [
      {
        team1: { name: 'team1', player1 : 'hs', player2: 'lo' },
        team2: { name: 'team2', player1 : 'hs', player2: 'lo' },
        nextMatch:
        {
          team1: { name: 'team1', player1 : 'hs', player2: 'lo' },
          team2: { name: 'team3', player1 : 'hs', player2: 'lo' },
          nextMatch:
          {
            team1: { name: 'team1', player1 : 'hs', player2: 'lo' },
            team2: { name: 'team5', player1 : 'hs', player2: 'lo' },
            nextMatch: {
              team1: { name: 'team1', player1 : 'hs', player2: 'lo' },
              team2: null,
              nextMatch: null
            }
          }
        }
      },
      {
        team1: { name: 'team3', player1 : 'hs', player2: 'lo' },
        team2: { name: 'team4', player1 : 'hs', player2: 'lo' },
        nextMatch: null
      },
      {
        team1: { name: 'team5', player1 : 'hs', player2: 'lo' },
        team2: { name: 'team6', player1 : 'hs', player2: 'lo' },
        nextMatch: {
          team1: { name: 'team5', player1 : 'hs', player2: 'lo' },
          team2: { name: 'team7', player1 : 'hs', player2: 'lo' },
          nextMatch: null
        }
      },
      {
        team1: { name: 'team7', player1 : 'hs', player2: 'lo' },
        team2: { name: 'team8', player1 : 'hs', player2: 'lo' },
        nextMatch: null
      }
    ]
  };

  constructor() { }

  ngOnInit() {
  }

}
