import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { DemoNgZorroAntdModule } from './app/NgZorroAntDesignModule';
import { ReactiveFormsModule } from '@angular/forms';
import { NzLayoutModule } from 'ng-zorro-antd/layout';


bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));


bootstrapApplication(AppComponent, {
  providers: [
    DemoNgZorroAntdModule,
    ReactiveFormsModule,
    NzLayoutModule
   // Add it here to provide it globally
  ]
});
