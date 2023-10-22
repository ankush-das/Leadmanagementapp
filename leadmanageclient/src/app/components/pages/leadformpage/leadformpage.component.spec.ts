import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadformpageComponent } from './leadformpage.component';

describe('LeadformpageComponent', () => {
  let component: LeadformpageComponent;
  let fixture: ComponentFixture<LeadformpageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeadformpageComponent]
    });
    fixture = TestBed.createComponent(LeadformpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
