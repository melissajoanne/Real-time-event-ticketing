import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';  // Import CommonModule if needed for ngIf, ngFor, etc.
import { Router } from '@angular/router';  // Import Router for navigation


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, HttpClientModule],  // Removed TicketCountComponent from here
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private http: HttpClient, private router: Router) {}

  title = 'RealTimeFrontEndAngular';

  ngOnInit() {
    // this.getData();
  }

  getData() {
    const apiUrl = 'https://jsonplaceholder.typicode.com/posts';  // Example API URL
    this.http.get(apiUrl).subscribe(
      (response) => {
        console.log('API Response:', response);
      },
      (error) => {
        console.error('Error occurred:', error);
      }
    );
  }

  goToAbout() {
    this.router.navigate(['/user-selection']);  // Navigate to the user selection page
  }
}

