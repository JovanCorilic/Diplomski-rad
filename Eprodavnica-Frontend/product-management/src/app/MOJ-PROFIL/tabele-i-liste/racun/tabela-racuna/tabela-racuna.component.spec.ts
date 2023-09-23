import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelaRacunaComponent } from './tabela-racuna.component';

describe('TabelaRacunaComponent', () => {
  let component: TabelaRacunaComponent;
  let fixture: ComponentFixture<TabelaRacunaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabelaRacunaComponent]
    });
    fixture = TestBed.createComponent(TabelaRacunaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
