import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaProduktPageComponent } from './lista-produkt-page.component';

describe('ListaProduktPageComponent', () => {
  let component: ListaProduktPageComponent;
  let fixture: ComponentFixture<ListaProduktPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaProduktPageComponent]
    });
    fixture = TestBed.createComponent(ListaProduktPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
