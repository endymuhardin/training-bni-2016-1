import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PesertaListComponent } from './peserta-list/peserta-list.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [PesertaListComponent],
  exports: [ PesertaListComponent ]
})
export class PesertaModule { }
