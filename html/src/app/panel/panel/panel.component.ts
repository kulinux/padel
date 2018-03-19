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
      {
        id: "hs",
        name : 'Han Solo'
      },
      {
        id: "lo",
        name : 'Leia Organa'
      }
    ],
    matches: [
      {
        team1: {
            player1 : 'hs',
            player2: 'lo'
        },
        team2: {
            player1 : 'hs',
            player2: 'lo'
        },
        nextMatch: {
          team1: {
              player1 : 'hs',
              player2: 'lo'
          },
          team2: {
              player1 : 'hs',
              player2: 'lo'
          },
          nextMatch: null
        }
      },
      {
        team1: {
            player1 : 'hs',
            player2: 'lo'
        },
        team2: {
            player1 : 'hs',
            player2: 'lo'
        },
        nextMatch: null
      }
    ]
  };

  constructor() { }

  ngOnInit() {
  }

}
