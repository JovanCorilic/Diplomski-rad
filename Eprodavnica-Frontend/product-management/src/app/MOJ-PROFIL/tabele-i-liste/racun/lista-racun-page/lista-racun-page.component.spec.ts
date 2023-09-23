import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaRacunPageComponent } from './lista-racun-page.component';

describe('ListaRacunPageComponent', () => {
  let component: ListaRacunPageComponent;
  let fixture: ComponentFixture<ListaRacunPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaRacunPageComponent]
    });
    fixture = TestBed.createComponent(ListaRacunPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
