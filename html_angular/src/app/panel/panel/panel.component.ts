import { Component, OnInit } from '@angular/core';
import {Tournament, Match, Team, Player} from '../../model/model'

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})


export class PanelComponent implements OnInit {

  tournament: Tournament;

  constructor() { }

  ngOnInit() {
    //this.tournament = this.mockTournamentJson();
    this.tournament = this.mockTournament();
  }

  mockTournament() : Tournament {
    let trn = new Tournament();
    let data = [1, 2, 3, 4]
    data
    .map(i => "" + i)
    .forEach(i => {
      let ply = new Player();
      ply.id = i;
      ply.name = "Player " + i;
      trn.players.push(ply);
    });

    data
    .forEach(i => {
      let team1 = new Team();
      team1.name = "team1_" + i;
      team1.player1 = "player1_" + i + "_1";
      team1.player2 = "player1_" + i + "_2";

      let team2 = new Team();
      team2.name = "team2_" + i;
      team2.player1 = "player2_" + i + "_1";
      team2.player2 = "player2_" + i + "_2";

      let match = new Match()
      match.team1 = team1;
      match.team2 = team2;

      match.nextMatch = new Match();
      match.nextMatch.nextMatch = new Match();

      trn.matches.push(match);
    } );
    return trn;
  }

  mockTournamentJson(): Tournament {
    return {
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
    }


}
