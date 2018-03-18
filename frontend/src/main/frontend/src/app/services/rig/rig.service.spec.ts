import { TestBed, inject } from '@angular/core/testing';

import { RigService } from './rig.service';

describe('RigService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RigService]
    });
  });

  it('should be created', inject([RigService], (service: RigService) => {
    expect(service).toBeTruthy();
  }));
});
