import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { RestockpageComponent } from './restockpage/restockpage.component';
import { ReturnpageComponent } from './returnpage/returnpage.component';
import { RentpageComponent } from './rentpage/rentpage.component';
import { RegisterpageComponent } from './registerpage/registerpage.component';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { CartpageComponent } from './cartpage/cartpage.component';
import { AuthGuard } from './shared/auth.guard';






const routes: Routes = [

  { path: 'login', component: LoginpageComponent },
  { path: 'register', component: RegisterpageComponent },

  { path: 'home', component: HomepageComponent, canActivate: [AuthGuard] },
  { path: 'restock', component: RestockpageComponent, canActivate: [AuthGuard] },
  { path: 'return', component: ReturnpageComponent, canActivate: [AuthGuard] },
  { path: 'rent', component: RentpageComponent, canActivate: [AuthGuard] },
  { path: 'cart', component: CartpageComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
