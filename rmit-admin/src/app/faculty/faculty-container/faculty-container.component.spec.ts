import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacultyContainerComponent } from './faculty-container.component';

describe('FacultyContainerComponent', () => {
  let component: FacultyContainerComponent;
  let fixture: ComponentFixture<FacultyContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacultyContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacultyContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
