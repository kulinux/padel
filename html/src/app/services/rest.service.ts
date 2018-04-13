import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { isDevMode } from '@angular/core';
import { Subject } from "rxjs/Subject";


import { MatchResult, Tournament } from '../model/model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class RestService {

  private getTournamentUrl: string = "/padel/tournaments/666";
  private winMatchUrl: string = "/padel/tournaments/666/winMatch";
  private mockMatchUrl = new Subject<string>();
  private mockTournament = new Subject<Tournament>();

  constructor(private http: HttpClient) { }

  getTournament(): Observable<Tournament> {
    if(isDevMode()) {
      return this.mockTournament.asObservable();
    }
    return this.http.get<Tournament>(this.getTournamentUrl)
  }

  win(match: MatchResult) : Observable<string> {
    if(isDevMode()) {
      return this.mockMatchUrl.asObservable();
    }
    return this.http.post<string>(this.winMatchUrl, match, httpOptions)
  }

}
