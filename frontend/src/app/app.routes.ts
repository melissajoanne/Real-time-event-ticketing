import { Routes } from '@angular/router';
import { UserSelectionComponent } from './Components/user-selection/user-selection.component';
import { VendorFormComponent } from './Components/vendor-form/vendor-form.component';
import { CustomerFormComponent } from './Components/customer-form/customer-form.component';
import { CustomerBuyComponent } from './Components/customer-buy/customer-buy.component';
import { CustomerReserveComponent } from './Components/customer-reserve/customer-reserve.component';
import { VendorReleaseComponent } from './Components/vendor-release/vendor-release.component';


export const routes: Routes = [
    { path: '', component: UserSelectionComponent },
    { path: 'user-selection', component: UserSelectionComponent },
    { path: 'vendor-form', component: VendorFormComponent },
    {path:'customer-form', component: CustomerFormComponent },
    {path:'customer-reserve',component:CustomerReserveComponent},
    {path:'customer-buy',component:CustomerBuyComponent},
    {path:'vendor-release',component:VendorReleaseComponent}

];
