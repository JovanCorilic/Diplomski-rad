import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndividualniRacunComponent } from './individualni-racun.component';

describe('IndividualniRacunComponent', () => {
  let component: IndividualniRacunComponent;
  let fixture: ComponentFixture<IndividualniRacunComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndividualniRacunComponent]
    });
    fixture = TestBed.createComponent(IndividualniRacunComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
