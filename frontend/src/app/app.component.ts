

import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, Inject, OnDestroy, OnInit, PLATFORM_ID } from '@angular/core';
import { Router,RouterModule } from '@angular/router';
import { RouterOutlet } from '@angular/router'

import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HttpClientModule,RouterModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
 
  
})
export class AppComponent implements OnInit, OnDestroy {
maxTicketCapacity: any;
capacity: any;
  title(title: any) {
    throw new Error('Method not implemented.');
  }
  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object,
  
  ) {}

  ticketCount: any;
  intervalId: any;

  ngOnInit() {
    
    if (isPlatformBrowser(this.platformId)) {
      // Only start polling on the client-side
      this.startPolling();
    }
  //   this.getmaxTicketCapacity();
  // }
  // getmaxTicketCapacity() {
  //   throw new Error('Method not implemented.');
   }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  startPolling() {
    this.getData(); // Initial fetch
    this.intervalId = setInterval(() => {
      this.getData(); // Repeatedly fetch data every interval (e.g., 15 seconds)
    }, 5000); 
  }

  getData() {
    const apiUrl = 'http://localhost:8080/ticket/count';
    this.http.get(apiUrl).subscribe(
      (response) => {
        console.log('API Response:', response);
        this.ticketCount = response.toString();
      },
      (error) => {
        console.error('Error occurred:', error);
      }
    );
  }

  goToAbout() {
    this.router.navigate(['/user-selection']);
  }

  // getMaxTicketCapacity() {
  //   this.ticketService.getMaxTicketCapacity().pipe(
  //     catchError(error => {
  //       console.error('Error fetching max ticket capacity', error);
  //       return of(null); // Return a default value or handle the error appropriately
  //     })
  //   ).subscribe(
  //     (capacity: any) => {
  //       this.maxTicketCapacity = capacity; // Update the max ticket capacity on success
  //     }
  //   );
  // }

}