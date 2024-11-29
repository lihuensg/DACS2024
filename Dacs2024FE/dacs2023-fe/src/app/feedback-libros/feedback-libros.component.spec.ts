import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackLibrosComponent } from './feedback-libros.component';

describe('FeedbackLibrosComponent', () => {
  let component: FeedbackLibrosComponent;
  let fixture: ComponentFixture<FeedbackLibrosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FeedbackLibrosComponent]
    });
    fixture = TestBed.createComponent(FeedbackLibrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
