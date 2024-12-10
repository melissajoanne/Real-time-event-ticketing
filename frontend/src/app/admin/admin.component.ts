import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { interval } from 'rxjs';

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
  vendorReleaseInterval: number | undefined;
  tickets: any[] = [];
  fetchInterval: any;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.startPolling();
  }

  ngOnDestroy(): void {
    if (this.fetchInterval) {
      this.fetchInterval.unsubscribe();
    }
  }

  onSubmit() {
    const config = {
      totalTickets: this.totalTickets,
      maxTicketCapacity: this.maxTicketCapacity,
      customerRetrievalRate: this.customerRetrievalRate,
      vendorReleaseInterval: this.vendorReleaseInterval
    };

    this.http.post('/form/config', config).subscribe(response => {
      console.log('Config submitted', response);
    });
  }

  start() {
    this.http.post('/admin/start', {}).subscribe(response => {
      console.log('Started', response);
    });
  }

  stop() {
    this.http.post('/admin/stop', {}).subscribe(response => {
      console.log('Stopped', response);
    });
  }

  fetchTickets() {
    this.http.get('http://localhost:8080/ticket/all').subscribe((data: any) => {
      this.tickets = data;
    });
  }
  startPolling() {
    this.fetchTickets(); // Fetch initially
    this.fetchInterval = interval(5000).subscribe(() => {
      this.fetchTickets();
    });
}
}
