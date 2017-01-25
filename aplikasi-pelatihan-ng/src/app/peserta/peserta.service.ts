import { Injectable } from '@angular/core';

import { Peserta } from './peserta.model';

@Injectable()
export class PesertaService {

  constructor() { }

  ambilDataPeserta() : Promise<Peserta[]> {
	  console.log("menjalankan function ambilDataPeserta")
	  let data : Peserta[] = [
		  new Peserta('Peserta 001', 'p001@coba.com', '081234567891', new Date()),
		  new Peserta('Peserta 002', 'p002@coba.com', '081234567892', new Date()),
		  new Peserta('Peserta 003', 'p003@coba.com', '081234567893', new Date())
	  ];
	  return Promise.resolve(data);
  };
}
