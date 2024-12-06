import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorReleaseComponent } from './vendor-release.component';

describe('VendorReleaseComponent', () => {
  let component: VendorReleaseComponent;
  let fixture: ComponentFixture<VendorReleaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VendorReleaseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorReleaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
