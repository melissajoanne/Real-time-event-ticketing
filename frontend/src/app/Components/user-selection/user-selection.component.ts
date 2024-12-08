import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router';  // Import Router
import { HttpClientModule } from '@angular/common/http';



@Component({
  selector: 'app-user-selection',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule],
  templateUrl: './user-selection.component.html',
  styleUrl: './user-selection.component.css'
})
export class UserSelectionComponent {
  ticketCount : any;

  
  constructor(private router: Router, private http: HttpClient) { }
  
  // ngOnInit() {
  //    setTimeout(() => this.getData(), 5000) ;
  // }

  goToVendor() {
    this.router.navigate(['/vendor-form']);  
  }
  goToCustomer(){
    this.router.navigate(['/customer-form']);
  }
  
  // getData() {
  //   const apiUrl = 'http://localhost:8080/ticket/count';  // Example API URL
  //   this.http.get(apiUrl).subscribe(
  //     (response) => {
  //       console.log('API Response:', response);
  //       this.ticketCount = response.toString();
  //     },
  //     (error) => {
  //       console.error('Error occurred:', error);
  //     }
  //   );
  // }

}
