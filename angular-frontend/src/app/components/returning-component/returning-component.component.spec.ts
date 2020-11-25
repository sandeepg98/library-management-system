import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturningComponentComponent } from './returning-component.component';

describe('ReturningComponentComponent', () => {
  let component: ReturningComponentComponent;
  let fixture: ComponentFixture<ReturningComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReturningComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturningComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
