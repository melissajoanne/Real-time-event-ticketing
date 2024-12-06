import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-vendor-release',
  templateUrl: './vendor-release.component.html',
  styleUrls: ['./vendor-release.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
})
export class VendorReleaseComponent implements OnInit {
  vendorId: string | null = null;
  ticketRelease = {
    ticketsPerRelease: '',
    ticketPrice: '',
    ticketType: ''
  };
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    // Ensure this only runs on the client-side (avoid SSR issues)
    if (typeof window !== 'undefined') {
      this.vendorId = sessionStorage.getItem('vendorId');
    }

    if (!this.vendorId) {
      this.errorMessage = 'Vendor ID not found in session storage. Please log in again.';
      console.error(this.errorMessage);
    }
  }

  onSubmit(ticketReleaseForm: NgForm): void {
    this.successMessage = '';
    this.errorMessage = '';
  
    if (ticketReleaseForm.valid && this.vendorId) {
      console.log("Vendor ID from session storage:", this.vendorId);
      const url = 'http://localhost:8080/ticket/vendor/release'; // Backend URL for ticket release
  
      const body = {
        ticketsPerRelease: this.ticketRelease.ticketsPerRelease,
        ticketPrice: this.ticketRelease.ticketPrice,
        ticketType: this.ticketRelease.ticketType
      };

      // Make the POST request to the backend, handling plain text response
      this.http.post(url, body, {
        headers: new HttpHeaders({
          'x-vendor-id': this.vendorId // Send vendor ID in headers
        }),
        responseType: 'text' // Ensure the response type is 'text'
      }).subscribe({
        next: (response: string) => {
          console.log('Ticket release response:', response);
          if (response === 'Ticket release process started.') {
            this.successMessage = response; // Display the success message
            this.errorMessage = ''; // Clear any previous errors
            this.router.navigate(['/user-selection']); // Optionally navigate to another page
          } else {
            this.errorMessage = 'Ticket release process started ' + response;
          }
        },
        error: (error) => {
          console.error('Error during ticket release:', error);
          this.errorMessage = 'Error during ticket release. Please try again later.';
          this.successMessage = ''; // Clear any success message
        }
      });
    } else {
      this.errorMessage = 'Please fill out the form correctly and ensure vendor ID is available.';
      console.error(this.errorMessage);
    }
  }
}
