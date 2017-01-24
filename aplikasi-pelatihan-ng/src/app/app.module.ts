import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Route }   from '@angular/router';

import { AppComponent } from './app.component';

import { PesertaModule } from './peserta/peserta.module';
import { AboutComponent } from './about/about.component';
import { HomeComponent } from './home/home.component';

const ROUTES: Route[] = [
	{path: 'about', component: AboutComponent},
	{path: 'peserta', redirectTo: '/peserta', pathMatch: 'full'},
	{path: '**', component: HomeComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
	RouterModule.forRoot(ROUTES),
	PesertaModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
