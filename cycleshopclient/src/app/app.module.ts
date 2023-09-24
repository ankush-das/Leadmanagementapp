import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { RestockpageComponent } from './restockpage/restockpage.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ReturnpageComponent } from './returnpage/returnpage.component';
import { RentpageComponent } from './rentpage/rentpage.component';
import { RegisterpageComponent } from './registerpage/registerpage.component';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { CartpageComponent } from './cartpage/cartpage.component';
import { HttpInterceptorService } from './shared/HttpInterceptorService';


@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    RestockpageComponent,
    NavbarComponent,
    ReturnpageComponent,
    RentpageComponent,
    RegisterpageComponent,
    LoginpageComponent,
    CartpageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
