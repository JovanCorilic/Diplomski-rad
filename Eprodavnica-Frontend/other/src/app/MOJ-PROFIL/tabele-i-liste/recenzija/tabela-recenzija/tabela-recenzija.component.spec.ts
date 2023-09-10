import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelaRecenzijaComponent } from './tabela-recenzija.component';

describe('TabelaRecenzijaComponent', () => {
  let component: TabelaRecenzijaComponent;
  let fixture: ComponentFixture<TabelaRecenzijaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabelaRecenzijaComponent]
    });
    fixture = TestBed.createComponent(TabelaRecenzijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
