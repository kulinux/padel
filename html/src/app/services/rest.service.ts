import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MatchResult, Tournament } from '../model/model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class RestService {

  private getTournamentUrl: string = "/padel/tournaments/666";
  private winMatchUrl: string = "/padel/tournaments/666/winMatch";

  constructor(private http: HttpClient) { }

  getTournament(): Observable<Tournament> {
    return this.http.get<Tournament>(this.getTournamentUrl)
  }

  win(match: MatchResult) {
    return this.http.post<MatchResult>(this.winMatchUrl, match, httpOptions)
  }

}
