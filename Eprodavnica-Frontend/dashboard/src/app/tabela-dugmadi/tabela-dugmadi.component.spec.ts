import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelaDugmadiComponent } from './tabela-dugmadi.component';

describe('TabelaDugmadiComponent', () => {
  let component: TabelaDugmadiComponent;
  let fixture: ComponentFixture<TabelaDugmadiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabelaDugmadiComponent]
    });
    fixture = TestBed.createComponent(TabelaDugmadiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
