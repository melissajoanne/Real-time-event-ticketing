import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { NgForm } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router'; 
import { HttpClientModule } from '@angular/common/http'; 
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-customer-reserve',
  standalone: true,
  imports: [RouterOutlet,HttpClientModule],
  templateUrl: './customer-reserve.component.html',
  styleUrl: './customer-reserve.component.css'
})
export class CustomerReserveComponent {
  imageName= 'tt.jpg';

  successMessage: string = '';
  errorMessage: string = '';
  ticket: any = {};  // Assuming ticket object is defined
  customerId: string | null = null;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  onSubmit(): void {
    // Ensure sessionStorage is accessed only in the browser
    if (isPlatformBrowser(this.platformId)) {
      // Retrieve customerId from sessionStorage
      const storedCustomerId = sessionStorage.getItem('customerId');
      
      if (!storedCustomerId) {
        this.errorMessage = 'No customer ID found. Please add a customer first.';
        console.error('No customerId in sessionStorage!');
        return; // Exit early if customerId is not found
      }

      // Prepare the ticket object
      const body = this.ticket;  // In case you need other data in the body

      // Log the ticket object
      console.log('Reserving ticket with customer ID:', storedCustomerId);

      // Set up the HTTP headers with the customerId
      const headers = { 'X-Customer-Id': storedCustomerId };

      // Send the POST request to reserve the ticket
      this.http.post('http://localhost:8080/ticket/reserve', body, { headers }).subscribe({
        next: (response: any) => {
          console.log('Ticket reserved successfully:', response);

          // Success message
          this.successMessage = 'Ticket reserved successfully!';
          this.errorMessage = '';  // Reset error message

          // Navigate to the customer-buy page after successful reservation
          this.router.navigate(['/customer-buy']);
        },
        error: (error) => {
          console.error('Error reserving ticket:', error);
          this.errorMessage = `Error reserving ticket: ${error.message || 'Unknown error'}`;
          this.successMessage = '';  // Reset success message
        }
      });
    } else {
      console.error('SessionStorage is not available in this environment');
      this.errorMessage = 'SessionStorage is not available in this environment.';
    }
  }
}
