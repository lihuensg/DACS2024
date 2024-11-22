import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisLibrosComponent } from './mis-libros.component';

describe('MisLibrosComponent', () => {
  let component: MisLibrosComponent;
  let fixture: ComponentFixture<MisLibrosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MisLibrosComponent]
    });
    fixture = TestBed.createComponent(MisLibrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
