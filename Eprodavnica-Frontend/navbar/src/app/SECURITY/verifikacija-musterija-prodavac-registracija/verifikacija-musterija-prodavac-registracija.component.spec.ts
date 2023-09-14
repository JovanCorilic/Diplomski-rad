import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifikacijaMusterijaProdavacRegistracijaComponent } from './verifikacija-musterija-prodavac-registracija.component';

describe('VerifikacijaMusterijaProdavacRegistracijaComponent', () => {
  let component: VerifikacijaMusterijaProdavacRegistracijaComponent;
  let fixture: ComponentFixture<VerifikacijaMusterijaProdavacRegistracijaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifikacijaMusterijaProdavacRegistracijaComponent]
    });
    fixture = TestBed.createComponent(VerifikacijaMusterijaProdavacRegistracijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
