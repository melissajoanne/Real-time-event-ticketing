// import { Component, OnDestroy, OnInit } from '@angular/core';
// import { RouterOutlet } from '@angular/router';
// import { HttpClient } from '@angular/common/http';
// import { HttpClientModule } from '@angular/common/http';
// import { CommonModule } from '@angular/common';  // Import CommonModule if needed for ngIf, ngFor, etc.
// import { Router } from '@angular/router';  // Import Router for navigation
// // import { WebSocketService } from './web-socket.service';


// @Component({
//   selector: 'app-root',
//   standalone: true,
//   imports: [RouterOutlet, CommonModule, HttpClientModule],  // Removed TicketCountComponent from here
//   templateUrl: './app.component.html',
//   styleUrls: ['./app.component.css']
// })
// export class AppComponent {
//   constructor(private http: HttpClient, private router: Router) {}

//   title = 'RealTimeFrontEndAngular';
//   ticketCount : any;
//   intervalId : any;

//   ngOnInit() {
//     this.getData();
//   }


//   // getData() {
//   //   const apiUrl = 'https://jsonplaceholder.typicode.com/posts';  // Example API URL
//   //   this.http.get(apiUrl).subscribe(
//   //     (response) => {
//   //       console.log('API Response:', response);
//   //     },
//   //     (error) => {
//   //       console.error('Error occurred:', error);
//   //     }
//   //   );
//   // }


//   getData() {
//     const apiUrl = 'http://localhost:8080/ticket/count';  // Example API URL
//     this.http.get(apiUrl).subscribe(
//       (response) => {
//         console.log('API Response:', response);
//         this.ticketCount = response.toString();
//       },
//       (error) => {
//         console.error('Error occurred:', error);
//       }
//     );
//   }
//   // ngOnInit() {
//   //   console.log('onit works')
//   //   // Establish WebSocket connection
//   //   this.webSocketService.connect().subscribe(
//   //     (message: string) => {
//   //       console.log('Received message:', message);
//   //       this.ticketCount = message;
//   //     },
//   //     (error) => {
//   //       console.error('WebSocket error:', error);
//   //     }
//   //   );
//   // }

//   // ngOnDestroy() {
//   //   // Disconnect when the component is destroyed
//   //   this.webSocketService.disconnect();
//   // }

//   // ngOnDestroy() {
//   //   // Clear the interval when the component is destroyed
//   //   if (this.intervalId) {
//   //     clearInterval(this.intervalId);
//   //   }
//   // }

//   goToAbout() {
//     this.router.navigate(['/user-selection']);  // Navigate to the user selection page
//   }
// }

import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, Inject, OnDestroy, OnInit, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { RouterOutlet } from '@angular/router'

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HttpClientModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title(title: any) {
    throw new Error('Method not implemented.');
  }
  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ticketCount: any;
  intervalId: any;

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      // Only start polling on the client-side
      this.startPolling();
    }
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
    }, 5000); // Change interval as needed
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
}
