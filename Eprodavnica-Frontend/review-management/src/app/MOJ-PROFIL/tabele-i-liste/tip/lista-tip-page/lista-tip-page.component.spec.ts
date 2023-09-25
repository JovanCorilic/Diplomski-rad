import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaTipPageComponent } from './lista-tip-page.component';

describe('ListaTipPageComponent', () => {
  let component: ListaTipPageComponent;
  let fixture: ComponentFixture<ListaTipPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaTipPageComponent]
    });
    fixture = TestBed.createComponent(ListaTipPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
