import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecenzijaCreateComponent } from './recenzija-create.component';

describe('RecenzijaCreateComponent', () => {
  let component: RecenzijaCreateComponent;
  let fixture: ComponentFixture<RecenzijaCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecenzijaCreateComponent]
    });
    fixture = TestBed.createComponent(RecenzijaCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
