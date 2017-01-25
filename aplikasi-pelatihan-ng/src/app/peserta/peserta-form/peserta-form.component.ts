import { Component, OnInit } from '@angular/core';
import { Router }   from '@angular/router';

import { PesertaService} from '../peserta.service';
import { Peserta } from '../peserta.model';

@Component({
  selector: 'peserta-form',
  templateUrl: './peserta-form.component.html',
  styleUrls: ['./peserta-form.component.css']
})
export class PesertaFormComponent implements OnInit {

  peserta : Peserta = new Peserta(null, null, null, null);

  constructor(private router: Router, private service : PesertaService) { }

  ngOnInit() {
  }

  simpan() {
	  this.service.simpanPeserta(this.peserta);
  }

}
