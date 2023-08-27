import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProduktEditComponent } from './produkt-edit.component';

describe('ProduktEditComponent', () => {
  let component: ProduktEditComponent;
  let fixture: ComponentFixture<ProduktEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProduktEditComponent]
    });
    fixture = TestBed.createComponent(ProduktEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
