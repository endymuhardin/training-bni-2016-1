import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule, Route }   from '@angular/router';

import { PesertaListComponent } from './peserta-list/peserta-list.component';
import { PesertaFormComponent } from './peserta-form/peserta-form.component';


const ROUTES_PESERTA: Route[] = [
	{path: 'peserta/form', component: PesertaFormComponent},
	{path: 'peserta', component: PesertaListComponent}
];

@NgModule({
  imports: [
    CommonModule,
	RouterModule.forChild(ROUTES_PESERTA)
  ],
  declarations: [PesertaListComponent, PesertaFormComponent],
  exports: [ PesertaListComponent ]
})
export class PesertaModule { }
