
  import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';  // Import Router for navigation
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-form',
  standalone: true,
  imports: [HttpClientModule, FormsModule,CommonModule],  // Include necessary modules
  templateUrl: './customer-form.component.html',
  styleUrls: ['./customer-form.component.css']
})
export class CustomerFormComponent {

  customer = {
    name: '',
    email: ''
  };

  successMessage: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}  // Inject Router to navigate

  extractCustomerId(message: string) {
    // Regular expression to match the value between square brackets
    const regex = /\[(.*?)\]/;

    // Apply regex to the message
    const match = message.match(regex);

    if (match && match[1]) {
      // match[1] contains the value between square brackets
      return match[1].trim();  // Trim any whitespace
    } else {
      return null;
    }
  }

  onSubmit(customerForm: NgForm) {
    if (customerForm.valid) {
      const url = 'http://localhost:8080/customer/add';  // Replace with your backend URL
      const body = this.customer;

      console.log('Submitting customer data:', body);

      // Send the POST request to the backend
      this.http.post(url, body, {
        headers: new HttpHeaders({
          // Add any additional headers if necessary
        })
      }).subscribe({
        next: (response: any) => {
          console.log('Customer added successfully:', response);

          const customerId = this.extractCustomerId(response?.message);  // Adjust based on actual response structure
          console.log("customer id",customerId);
          if (customerId) {
            // Store the customerId in sessionStorage for future use
            sessionStorage.setItem('customerId', customerId.toString());
            console.log('Customer ID saved in sessionStorage:', customerId);

            // Success message
            this.successMessage = `Customer added successfully! Your customer ID is: ${customerId}`;
            this.errorMessage = '';  // Reset error message

            // Navigate to the customer-reserve page
            this.router.navigate(['/customer-reserve']);
          } else {
            this.errorMessage = 'Customer ID not returned in the response.';
          }

          // Reset the form after submission
          customerForm.reset();
        },
        error: (error) => {
          console.error('Error adding customer:', error);
          // Display error message
          this.errorMessage = 'Error adding customer. Please try again later.';
          this.successMessage = '';  // Reset success message
        }
      });
    } else {
      console.log('Form is invalid');
      this.errorMessage = 'Please fill out the form correctly.';
    }
  }
}
