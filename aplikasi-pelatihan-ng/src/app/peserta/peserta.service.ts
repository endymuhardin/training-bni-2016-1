import { Injectable } from '@angular/core';

import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { Peserta } from './peserta.model';

@Injectable()
export class PesertaService {

  serverUrl : string = "/api/peserta/";

  constructor(private httpClient: Http) { }

  ambilDataPeserta() : Promise<Peserta[]> {
	  console.log("menjalankan function ambilDataPeserta");

	  return this.httpClient.get(this.serverUrl)
	  .toPromise()
	  .then(response => response.json().content as Peserta[])
      .catch(this.handleError);

  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
