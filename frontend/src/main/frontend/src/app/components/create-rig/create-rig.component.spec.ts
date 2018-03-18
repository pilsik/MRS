import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRigComponent } from './create-rig.component';

describe('CreateRigComponent', () => {
  let component: CreateRigComponent;
  let fixture: ComponentFixture<CreateRigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateRigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateRigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
