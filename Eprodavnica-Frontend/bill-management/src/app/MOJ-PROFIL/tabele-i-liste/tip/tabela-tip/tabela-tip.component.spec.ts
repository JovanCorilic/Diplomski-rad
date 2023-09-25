import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelaTipComponent } from './tabela-tip.component';

describe('TabelaTipComponent', () => {
  let component: TabelaTipComponent;
  let fixture: ComponentFixture<TabelaTipComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabelaTipComponent]
    });
    fixture = TestBed.createComponent(TabelaTipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
