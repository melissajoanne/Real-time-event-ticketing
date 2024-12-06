import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { CustomerFormComponent } from './app/Components/customer-form/customer-form.component';
import { CustomerBuyComponent } from './app/Components/customer-buy/customer-buy.component';
import { CustomerReserveComponent } from './app/Components/customer-reserve/customer-reserve.component';
import { VendorFormComponent } from './app/Components/vendor-form/vendor-form.component';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));

//  platformBrowserDynamic().bootstrapModule(CustomerFormComponent)
//   .catch(err => console.error(err));

//   platformBrowserDynamic().bootstrapModule(CustomerReserveComponent)
//   .catch(err => console.error(err));

//   platformBrowserDynamic().bootstrapModule(CustomerBuyComponent)
//   .catch(err => console.error(err));

bootstrapApplication(VendorFormComponent, {
  providers: [
    provideHttpClient(),  // Provide HttpClient here
    provideRouter(routes),  // Your routes if you have any
  ]
}).catch(err => console.error(err));



