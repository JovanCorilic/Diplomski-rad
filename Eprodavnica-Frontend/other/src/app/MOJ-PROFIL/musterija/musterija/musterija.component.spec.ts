import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusterijaComponent } from './musterija.component';

describe('MusterijaComponent', () => {
  let component: MusterijaComponent;
  let fixture: ComponentFixture<MusterijaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MusterijaComponent]
    });
    fixture = TestBed.createComponent(MusterijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
