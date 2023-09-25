import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaRecenzijaPageComponent } from './lista-recenzija-page.component';

describe('ListaRecenzijaPageComponent', () => {
  let component: ListaRecenzijaPageComponent;
  let fixture: ComponentFixture<ListaRecenzijaPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaRecenzijaPageComponent]
    });
    fixture = TestBed.createComponent(ListaRecenzijaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
