import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DevolverLibrosComponent } from './devolver-libros.component';

describe('DevolverLibrosComponent', () => {
  let component: DevolverLibrosComponent;
  let fixture: ComponentFixture<DevolverLibrosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DevolverLibrosComponent]
    });
    fixture = TestBed.createComponent(DevolverLibrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
