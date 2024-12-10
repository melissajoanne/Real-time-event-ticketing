import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig as importedAppConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { CustomerFormComponent } from './app/Components/customer-form/customer-form.component';
import { CustomerBuyComponent } from './app/Components/customer-buy/customer-buy.component';
import { CustomerReserveComponent } from './app/Components/customer-reserve/customer-reserve.component';
import { VendorFormComponent } from './app/Components/vendor-form/vendor-form.component';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { ApplicationRef } from '@angular/core';
import { provideZoneChangeDetection } from '@angular/core';
import { filter, first } from 'rxjs';

// bootstrapApplication(AppComponent, appConfig)
//   .catch((err) => console.error(err));
const localAppConfig = {
  providers: [
    provideHttpClient(),
    provideRouter(routes),
    provideZoneChangeDetection()
  ]
};

// Bootstrap the application
bootstrapApplication(AppComponent, localAppConfig)
  .then(appRef => {
    // Ensure the application becomes stable
    appRef.isStable
      .pipe(
        filter(stable => stable),
        first()
      )
      .subscribe(() => {
        console.log('Application is stable');
      });
  })
  .catch(err => console.error(err));




