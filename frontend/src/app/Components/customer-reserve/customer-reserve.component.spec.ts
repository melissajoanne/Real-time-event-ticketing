import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerReserveComponent } from './customer-reserve.component';

describe('CustomerReserveComponent', () => {
  let component: CustomerReserveComponent;
  let fixture: ComponentFixture<CustomerReserveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerReserveComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerReserveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
