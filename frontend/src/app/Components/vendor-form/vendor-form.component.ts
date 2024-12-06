import { Component } from '@angular/core'; 
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-vendor-form',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './vendor-form.component.html',
  styleUrls: ['./vendor-form.component.css']
})
export class VendorFormComponent {
  vendor = {
    name: '',
    email: ''
  };

  successMessage: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit(vendorForm: NgForm) {
    if (vendorForm.valid) {
      const url = 'http://localhost:8080/vendor/add';  // Backend URL for vendor creation
      const body = this.vendor;
  
      console.log('Submitting vendor data:', body);
  
      // Send the POST request to the backend
      this.http.post(url, body).subscribe({
        next: (response: any) => {
          console.log('Response from backend:', response);  // Log the response to see its structure
  
          // Ensure you are extracting vendorId correctly from the response
          const vendorId = response.vendorId;  // Assuming this is in the response object
          console.log("Vendor ID:", vendorId);
  
          if (vendorId !== null && vendorId !== undefined) {
            // Store the vendorId in sessionStorage for future use
            sessionStorage.setItem('vendorId', vendorId.toString());
            console.log('Vendor ID saved in sessionStorage:', vendorId);
  
            // Success message
            this.successMessage = `Vendor added successfully! Your vendor ID is: ${vendorId}`;
            this.errorMessage = '';  // Reset error message
  
            // Navigate to the vendor-release page only after successful submission
            this.router.navigate(['/vendor-release']);
          } else {
            this.errorMessage = 'Vendor ID not returned in the response.';
          }
  
          // Reset the form after submission
          vendorForm.reset();
        },
        error: (error) => {
          console.error('Error adding vendor:', error);
          this.errorMessage = 'Error adding vendor. Please try again later.';
          this.successMessage = '';  // Reset success message
        }
      });
    } else {
      console.log('Form is invalid');
      this.errorMessage = 'Please fill out the form correctly.';
    }
  }
}




