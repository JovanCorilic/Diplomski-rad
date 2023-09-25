import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaKorisnikPageComponent } from './lista-korisnik-page.component';

describe('ListaKorisnikPageComponent', () => {
  let component: ListaKorisnikPageComponent;
  let fixture: ComponentFixture<ListaKorisnikPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaKorisnikPageComponent]
    });
    fixture = TestBed.createComponent(ListaKorisnikPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
