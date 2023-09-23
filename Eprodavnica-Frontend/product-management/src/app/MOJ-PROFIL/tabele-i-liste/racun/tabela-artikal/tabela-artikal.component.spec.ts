import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelaArtikalComponent } from './tabela-artikal.component';

describe('TabelaArtikalComponent', () => {
  let component: TabelaArtikalComponent;
  let fixture: ComponentFixture<TabelaArtikalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabelaArtikalComponent]
    });
    fixture = TestBed.createComponent(TabelaArtikalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
