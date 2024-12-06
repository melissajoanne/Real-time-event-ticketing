import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-payment-success',
  templateUrl: './payment-success.component.html',
  styleUrls: ['./payment-success.component.css']
})
export class PaymentSuccessComponent implements OnInit {
  
  successMessage: string = 'Your payment was successful!';
  errorMessage: string = '';

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Fetch query params (status, error message, etc.)
    this.route.queryParams.subscribe(params => {
      if (params['status'] === 'error') {
        this.errorMessage = 'There was an issue with your payment. Please try again.';
      } else if (params['status'] === 'timeout') {
        this.errorMessage = 'Payment timed out. Please try again later.';
      }
    });
  }
}

