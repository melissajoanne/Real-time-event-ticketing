import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private stompClient: any;

  constructor() { console.log("web skocket serveice running *************")}

  connect(): Observable<any> {
    const socket = new SockJS('http://localhost:8080/ws');  // WebSocket URL
    this.stompClient = Stomp.over(socket);
    console.log('function works')
    return new Observable(observer => {
      this.stompClient.connect({}, (frame: any) => {
        console.log('Connected: ' + frame);
        
        // Subscribe to the topic where the ticket count is sent
        this.stompClient.subscribe('/topic/ticketCount', (message: any) => {
          observer.next(message.body);
        });
      }, (error: any) => {
        observer.error(error);
      });
    });
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }
}
