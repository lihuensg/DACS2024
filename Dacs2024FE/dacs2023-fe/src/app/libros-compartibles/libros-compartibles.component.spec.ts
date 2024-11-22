import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LibrosCompartiblesComponent } from './libros-compartibles.component';

describe('LibrosCompartiblesComponent', () => {
  let component: LibrosCompartiblesComponent;
  let fixture: ComponentFixture<LibrosCompartiblesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LibrosCompartiblesComponent]
    });
    fixture = TestBed.createComponent(LibrosCompartiblesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
