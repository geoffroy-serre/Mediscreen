import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointementsCalendarComponent } from './appointements-calendar.component';

describe('AppointementsCalendarComponent', () => {
  let component: AppointementsCalendarComponent;
  let fixture: ComponentFixture<AppointementsCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppointementsCalendarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointementsCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
