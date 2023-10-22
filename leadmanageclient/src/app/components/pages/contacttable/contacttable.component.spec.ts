import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContacttableComponent } from './contacttable.component';

describe('ContacttableComponent', () => {
  let component: ContacttableComponent;
  let fixture: ComponentFixture<ContacttableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContacttableComponent]
    });
    fixture = TestBed.createComponent(ContacttableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
