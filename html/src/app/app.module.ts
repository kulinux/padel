import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { PanelComponent } from './panel/panel/panel.component';
import { PlayerComponent } from './panel/player/player.component';
import { MatchComponent } from './panel/match/match.component';
import { MatchesComponent } from './panel/matches/matches.component';


@NgModule({
  declarations: [
    AppComponent,
    PanelComponent,
    PlayerComponent,
    MatchComponent,
    MatchesComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
