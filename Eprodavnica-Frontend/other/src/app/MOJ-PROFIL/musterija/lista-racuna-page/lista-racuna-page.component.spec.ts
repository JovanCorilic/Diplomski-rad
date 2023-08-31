import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaRacunaPageComponent } from './lista-racuna-page.component';

describe('ListaRacunaPageComponent', () => {
  let component: ListaRacunaPageComponent;
  let fixture: ComponentFixture<ListaRacunaPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaRacunaPageComponent]
    });
    fixture = TestBed.createComponent(ListaRacunaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
