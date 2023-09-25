import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionpageComponent } from './sectionpage.component';

describe('SectionpageComponent', () => {
  let component: SectionpageComponent;
  let fixture: ComponentFixture<SectionpageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SectionpageComponent]
    });
    fixture = TestBed.createComponent(SectionpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
