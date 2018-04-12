import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http'


import { AppComponent } from './app.component';
import { PanelComponent } from './panel/panel/panel.component';
import { PlayerComponent } from './panel/player/player.component';
import { MatchComponent } from './panel/match/match.component';
import { MatchesComponent } from './panel/matches/matches.component';
import { RestService } from './services/rest.service';


@NgModule({
  declarations: [
    AppComponent,
    PanelComponent,
    PlayerComponent,
    MatchComponent,
    MatchesComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule
  ],
  providers: [RestService],
  bootstrap: [AppComponent]
})
export class AppModule { }
