import { TestBed } from '@angular/core/testing';

import { DiabetesEstimationService } from './diabetes-estimation.service';

describe('DiabetesEstimationService', () => {
  let service: DiabetesEstimationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiabetesEstimationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
