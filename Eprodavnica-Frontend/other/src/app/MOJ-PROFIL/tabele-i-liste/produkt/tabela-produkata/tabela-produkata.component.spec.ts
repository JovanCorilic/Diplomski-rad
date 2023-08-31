import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabelaProdukataComponent } from './tabela-produkata.component';

describe('TabelaProdukataComponent', () => {
  let component: TabelaProdukataComponent;
  let fixture: ComponentFixture<TabelaProdukataComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabelaProdukataComponent]
    });
    fixture = TestBed.createComponent(TabelaProdukataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
