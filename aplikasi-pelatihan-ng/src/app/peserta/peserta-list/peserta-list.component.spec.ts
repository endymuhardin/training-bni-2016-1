/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PesertaListComponent } from './peserta-list.component';

describe('PesertaListComponent', () => {
  let component: PesertaListComponent;
  let fixture: ComponentFixture<PesertaListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PesertaListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PesertaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
