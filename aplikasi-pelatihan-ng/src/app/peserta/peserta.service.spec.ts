/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PesertaService } from './peserta.service';

describe('PesertaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PesertaService]
    });
  });

  it('should ...', inject([PesertaService], (service: PesertaService) => {
    expect(service).toBeTruthy();
  }));
});
