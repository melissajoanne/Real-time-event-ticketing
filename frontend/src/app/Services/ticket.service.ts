import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { RouterOutlet } from '@angular/router'
import { HttpClientModule } from '@angular/common/http';
import { response } from 'express';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  // private apiUrl = 'http://localhost:8080/ticket/maxTicketCapacity' // Replace with your actual API URL

  maxTicketCapacity: any;

  constructor(private http: HttpClient) {}
  
  
  getMaxTicketCapacity(){
  const apiUrl = 'http://localhost:8080/ticket/maxTicketCapacity';
  this.http.get(apiUrl).subscribe(
    (response: any) => {
      this.maxTicketCapacity = response.toString();
      console.log('API Response:', response);
      this.getMaxTicketCapacity = response.toString();
    },
    (error) => {
      console.error('Error occurred:', error);
    }
  );
}
  }


