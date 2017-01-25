import { Component, OnInit } from '@angular/core';
import { Router }   from '@angular/router';

@Component({
  selector: 'peserta-form',
  templateUrl: './peserta-form.component.html',
  styleUrls: ['./peserta-form.component.css']
})
export class PesertaFormComponent implements OnInit {

  peserta = {};

  constructor(private router: Router) { }

  ngOnInit() {
  }

  simpan() {
	  this.router.navigate(['/peserta']);
  }

}
