import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifikacijaAdminRegistracijaComponent } from './verifikacija-admin-registracija.component';

describe('VerifikacijaAdminRegistracijaComponent', () => {
  let component: VerifikacijaAdminRegistracijaComponent;
  let fixture: ComponentFixture<VerifikacijaAdminRegistracijaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifikacijaAdminRegistracijaComponent]
    });
    fixture = TestBed.createComponent(VerifikacijaAdminRegistracijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
