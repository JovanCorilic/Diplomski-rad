import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelaKorisnikComponent } from './tabela-korisnik.component';

describe('TabelaKorisnikComponent', () => {
  let component: TabelaKorisnikComponent;
  let fixture: ComponentFixture<TabelaKorisnikComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabelaKorisnikComponent]
    });
    fixture = TestBed.createComponent(TabelaKorisnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
