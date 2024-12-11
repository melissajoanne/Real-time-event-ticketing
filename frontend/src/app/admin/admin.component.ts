// import { Component, OnDestroy, OnInit } from '@angular/core';
// import { HttpClient,HttpHeaders } from '@angular/common/http';
// import { Router } from '@angular/router';
// import { RouterOutlet } from '@angular/router';
// import { HttpClientModule } from '@angular/common/http';
// import { FormsModule } from '@angular/forms';
// import { CommonModule } from '@angular/common';
// import { interval } from 'rxjs';
// import { catchError } from 'rxjs/operators';
// import { of } from 'rxjs';


// @Component({
//   selector: 'app-admin',
//   standalone: true,
//   imports: [RouterOutlet, HttpClientModule, FormsModule, CommonModule],
//   templateUrl: './admin.component.html',
//   styleUrls: ['./admin.component.css']
// })
// export class AdminComponent implements OnInit, OnDestroy {
//   totalTickets: number | undefined;
//   maxTicketCapacity: number | undefined;
//   customerRetrievalRate: number | undefined;
//   tickets: any[] = [];
//   fetchInterval: any;
//   ticketReleaseRate: any;

//   constructor(private http: HttpClient) {}

//   ngOnInit(): void {
//     this.startPolling();
//   }

  

//   onSubmit() {
//     const config = {
//       totalTickets: this.totalTickets,
//       maxTicketCapacity: this.maxTicketCapacity,
//       customerRetrievalRate: this.customerRetrievalRate,
//       ticketReleaseRate: this.ticketReleaseRate
//     };

//     this.http.post('http://localhost:8080/ticket/config', config).subscribe(response => {
//       try {
//         const jsonResponse = JSON.parse(response as any);
//         console.log('Config submitted', jsonResponse);
//       } catch (error) {
//         console.error('Error parsing response', error);
//       }
//     }, error => {
//       console.error('Error submitting config', error);
//     });
//   }

//   start() {
//     this.http.post('http://localhost:8080/ticket/start', {}).subscribe(response => {
//       console.log('Started', response);
//     }, error => {
//       console.error('Error starting', error);
//     });
//   }

//   stop() {
//     this.http.post('http://localhost:8080/ticket/stop', {}).subscribe(response => {
//       console.log('Stopped', response);
//     });
//   }

//   // fetchTickets() {
//   //   this.http.get('http://localhost:8080/ticket/all').subscribe((data: any) => {
//   //     this.tickets = data;
//   //   });
//   // }
//   fetchTickets() {
   
//     this.http.get('http://localhost:8080/ticket/all')
//       .pipe(
//         catchError(error => {
//           console.error('Error fetching tickets', error);
//           return [];
          
//         }
//       )
//       )
//       .subscribe((data: any) => {
//         this.tickets = data;
      
//       });
//   }

//   startPolling() {
//     this.fetchTickets(); // Fetch initially
//     this.fetchInterval = interval(5000).subscribe(() => {
//       this.fetchTickets();
//     });
//   }

//     ngOnDestroy(): void {
//       if (this.fetchInterval) {
//         this.fetchInterval.unsubscribe();
//       }
//     }

// }
import { Component, OnDestroy, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule, FormsModule, CommonModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {
  totalTickets: number | undefined;
  maxTicketCapacity: number | undefined;
  customerRetrievalRate: number | undefined;
  tickets: any[] = [];
  fetchInterval: Subscription | undefined;
  ticketReleaseRate: any;
  isBrowser: boolean;
  isSimulationRunning: boolean = false;


  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: Object) {
    this.isBrowser = isPlatformBrowser(this.platformId);
    
  }

  ngOnInit(): void {
    if (this.isBrowser) {
      this.startPolling();
    }
  }

  onSubmit() {
    const config = {
      totalTickets: this.totalTickets,
      maxTicketCapacity: this.maxTicketCapacity,
      customerRetrievalRate: this.customerRetrievalRate,
      ticketReleaseRate: this.ticketReleaseRate
    };

    this.http.post('http://localhost:8080/config/update', config).subscribe(
      response => {
        console.log('Config submitted', response);
      },
      error => {
        console.error('Error submitting config', error);
      }
    );
  }

  // start() {
  //   this.http.post('http://localhost:8080/ticket/start', {}).subscribe(
  //     response => {
  //       console.log('Started', response);
  //     },
  //     error => {
  //       console.error('Error starting', error);
  //     }
  //   );
  // }

  // stop() {
  //   this.http.post('http://localhost:8080/ticket/stop', {}).subscribe(
  //     response => {
  //       console.log('Stopped', response);
  //     },
  //     error => {
  //       console.error('Error stopping', error);
  //     }
  //   );
  // }

  start() {
    if (this.isSimulationRunning) {
      console.log('Simulation is already in progress');
      return;
    }

    this.http.post('http://localhost:8080/ticket/start', {}).subscribe(
      response => {
        console.log('Started', response);
        this.isSimulationRunning = true;
      },
      error => {
        console.error('Error starting', error);
      }
    );
  }

  stop() {
    if (!this.isSimulationRunning) {
      console.log('Simulation is not running');
      return;
    }

    this.http.post('http://localhost:8080/ticket/stop', {}).subscribe(
      response => {
        console.log('Stopped', response);
        this.isSimulationRunning = false;
      },
      error => {
        console.error('Error stopping', error);
      }
    );
  }

  getSimulationStatus(): string {
    return this.isSimulationRunning ? 'Simulation is running' : 'Simulation is stopped';
  }
  fetchTickets() {
    this.http.get('http://localhost:8080/ticket/all').pipe(
      catchError(error => {
        console.error('Error fetching tickets', error);
        return of([]); // Return empty array on error
      })
    ).subscribe((data: any) => {
      this.tickets = data;
    });
  }

  startPolling() {
    this.fetchTickets(); // Fetch initially
    this.fetchInterval = interval(5000).subscribe(() => {
      this.fetchTickets();
    });
  }

  ngOnDestroy(): void {
    if (this.fetchInterval) {
      this.fetchInterval.unsubscribe();
    }
  }
}

