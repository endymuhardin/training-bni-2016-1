import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RouterModule, Route }   from '@angular/router';

import { PesertaListComponent } from './peserta-list/peserta-list.component';
import { PesertaFormComponent } from './peserta-form/peserta-form.component';
import { Peserta } from './peserta.model';
import { PesertaService } from './peserta.service';

const ROUTES_PESERTA: Route[] = [
	{path: 'peserta/form', component: PesertaFormComponent},
	{path: 'peserta', component: PesertaListComponent}
];

@NgModule({
  imports: [
    CommonModule,
	FormsModule,
	RouterModule.forChild(ROUTES_PESERTA)
  ],
  declarations: [PesertaListComponent, PesertaFormComponent],
  exports: [ PesertaListComponent ],
  providers: [ PesertaService ]
})
export class PesertaModule { }
