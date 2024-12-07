// import { isPlatformBrowser } from '@angular/common';
// import { Component, Inject, PLATFORM_ID } from '@angular/core';
// import { Router, RouterOutlet } from '@angular/router';  // Import Router for navigation
// import { HttpClientModule, HttpClient } from '@angular/common/http'; 
// import { FormsModule } from '@angular/forms';
// import { NgModule } from '@angular/core';

// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-customer-buy',
//   standalone: true,
//   imports: [RouterOutlet,HttpClientModule,FormsModule],  // Standalone components don't need the RouterModule here
//   templateUrl: './customer-buy.component.html',
//   styleUrls: ['./customer-buy.component.css']
// })
// export class CustomerBuyComponent {

//   successMessage: string = '';
//   errorMessage: string = '';
//   ticket: any = {};  // Assuming ticket object is defined
//   customerId: string = '';
// paymentDetails: any;

//   constructor(
//     private http: HttpClient,
//     private router: Router,
//     @Inject(PLATFORM_ID) private platformId: Object
//   ) {}

//   onPurchase(): void {
//     console.log('Reserving ticket with customer ID:');

//     //Ensure sessionStorage is accessed only in the browser
//     if (isPlatformBrowser(this.platformId)) {
//       // Retrieve customerId from sessionStorage
//       const storedCustomerId = sessionStorage.getItem('customerId');
      
//       if (!storedCustomerId) {
//         this.errorMessage = 'No customer ID found. Please add a customer first.';
//         console.error('No customerId in sessionStorage!');
//         return; // Exit early if customerId is not found
//       }

//       // Prepare the ticket object
//       const body = {};  // In case you need other data in the body

//       // Log the ticket object
//       console.log('Reserving ticket with customer ID:', storedCustomerId);

//       // Set up the HTTP headers with the customerId
//       const headers = { 'X-Customer-Id': storedCustomerId };


//       // Send the POST request to reserve the ticket
//       this.http.post('http://localhost:8080/ticket/finalize', body, { headers }).subscribe({
//         next: (response: any) => {
//           console.log('Ticket purchased successfully:', response);

//           // Success message
//           this.successMessage = 'Ticket purchased successfully!';
//           this.errorMessage = '';  // Reset error message

//           // Navigate to the customer-buy page after successful reservation
//           // this.router.navigate(['/user-selection']);
//         },
//         error: (error) => {
//           console.error('Error purchasing ticket:', error);
//           this.errorMessage = `Error purchasing ticket: ${error.message || 'Unknown error'}`;
//           this.successMessage = '';  // Reset success message
//         }
//       });
//     } else {
//       console.error('SessionStorage is not available in this environment');
//       this.errorMessage = 'SessionStorage is not available in this environment.';
//     }
//   }

//   // Method for completing the purchase
//   completePurchase() {
//     // Assume the purchase logic here
//     console.log('Purchase completed.');
  
//    // Navigate to the 'user-selection' route
//     this.router.navigate(['/user-selection']);
//   }
// }
import { isPlatformBrowser } from '@angular/common';
import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { HttpClientModule, HttpClient } from '@angular/common/http'; 
import { FormsModule, NgForm} from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-buy',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule, FormsModule,CommonModule],
  templateUrl: './customer-buy.component.html',
  styleUrls: ['./customer-buy.component.css']
})
export class CustomerBuyComponent {
  successMessage: string = '';  // To show success message
  errorMessage: string = '';    // To show error message
  ticket: any = {};  // Assuming ticket object is populated later
  customerId: string = '';
  paymentDetails: any;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  onPurchase(): void {
    console.log('Purchasing ticket with customer ID:');

    if (isPlatformBrowser(this.platformId)) {
      const storedCustomerId = sessionStorage.getItem('customerId');
      
      if (!storedCustomerId) {
        this.errorMessage = 'No customer ID found. Please add a customer first.';
        console.error('No customerId in sessionStorage!');
        return; // Exit early if customerId is not found
      }

      // Prepare the ticket object with necessary details
      const body = {
        customerId: storedCustomerId,
        ticketType: this.ticket.type,  // Assuming ticket object has a 'type' field
        paymentDetails: this.paymentDetails
      };

      console.log('Reserving ticket with customer ID:', storedCustomerId);

      const headers = { 'X-Customer-Id': storedCustomerId };

      this.http.post('http://localhost:8080/ticket/finalize', body, { headers }).subscribe({
        next: (response: any) => {
          console.log('Ticket purchased successfully:', response);
          this.successMessage = 'Ticket purchased successfully!';  // Set success message
          this.errorMessage = '';  // Reset error message

          // Navigate to the 'user-selection' route after successful purchase
          setTimeout(() => {
            this.router.navigate(['/user-selection']);
          }, 5000); // Delay navigation to show the success message
        },
        error: (error) => {
          console.error('Error purchasing ticket:', error);
          this.errorMessage = `Session Time Out, Error purchasing ticket: ${error.message || 'Unknown error'}`;
          this.successMessage = '';  // Reset success message
        }
      });
    } else {
      console.error('SessionStorage is not available in this environment');
      this.errorMessage = 'SessionStorage is not available in this environment.';
    }
  }

  completePurchase() {
    console.log('Purchase completed.');
    this.router.navigate(['/user-selection']);
  }
}

