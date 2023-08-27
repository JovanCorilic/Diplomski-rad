import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProduktCreateComponent } from './produkt-create.component';

describe('ProduktCreateComponent', () => {
  let component: ProduktCreateComponent;
  let fixture: ComponentFixture<ProduktCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProduktCreateComponent]
    });
    fixture = TestBed.createComponent(ProduktCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
