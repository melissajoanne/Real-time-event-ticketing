import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router';  // Import Router


@Component({
  selector: 'app-user-selection',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './user-selection.component.html',
  styleUrl: './user-selection.component.css'
})
export class UserSelectionComponent {

  
  constructor(private router: Router) { }
  

  goToVendor() {
    this.router.navigate(['/vendor-form']);  
  }
  goToCustomer(){
    this.router.navigate(['/customer-form']);
  }

}
