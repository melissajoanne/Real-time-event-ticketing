import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private ticketCountUrl = 'http://localhost:8080/ticket/count'; // Your backend URL

  constructor(private http: HttpClient) {}

  getTicketCount(): Observable<number> {
    return this.http.get<number>(this.ticketCountUrl); // Send GET request to backend
  }
}


