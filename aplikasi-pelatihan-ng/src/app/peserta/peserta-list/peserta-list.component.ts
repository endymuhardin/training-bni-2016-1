import { Component, OnInit } from '@angular/core';

import { Peserta } from '../peserta.model';
import { PesertaService } from '../peserta.service';

@Component({
  selector: 'peserta-list',
  templateUrl: './peserta-list.component.html',
  styleUrls: ['./peserta-list.component.css']
})
export class PesertaListComponent implements OnInit {

  dataPeserta : Peserta[] = [];

  constructor(private service : PesertaService) {

  }

  ngOnInit() {
	  this.service.ambilDataPeserta().then(
		  data => {
			  this.dataPeserta = data;
			  console.log("Jumlah data dari service "+data.length);
		  }
	  );

  }

}
