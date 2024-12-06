import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerBuyComponent } from './customer-buy.component';

describe('CustomerBuyComponent', () => {
  let component: CustomerBuyComponent;
  let fixture: ComponentFixture<CustomerBuyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerBuyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerBuyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
