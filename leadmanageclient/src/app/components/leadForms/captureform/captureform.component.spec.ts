import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaptureformComponent } from './captureform.component';

describe('CaptureformComponent', () => {
  let component: CaptureformComponent;
  let fixture: ComponentFixture<CaptureformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CaptureformComponent]
    });
    fixture = TestBed.createComponent(CaptureformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
