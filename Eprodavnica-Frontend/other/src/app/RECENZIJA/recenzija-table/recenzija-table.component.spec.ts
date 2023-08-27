import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecenzijaTableComponent } from './recenzija-table.component';

describe('RecenzijaTableComponent', () => {
  let component: RecenzijaTableComponent;
  let fixture: ComponentFixture<RecenzijaTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecenzijaTableComponent]
    });
    fixture = TestBed.createComponent(RecenzijaTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
