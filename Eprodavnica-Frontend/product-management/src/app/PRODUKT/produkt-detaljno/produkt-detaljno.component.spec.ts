import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProduktDetaljnoComponent } from './produkt-detaljno.component';

describe('ProduktDetaljnoComponent', () => {
  let component: ProduktDetaljnoComponent;
  let fixture: ComponentFixture<ProduktDetaljnoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProduktDetaljnoComponent]
    });
    fixture = TestBed.createComponent(ProduktDetaljnoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
