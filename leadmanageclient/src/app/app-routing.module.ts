import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './components/LoginRegistration/register/register.component';
import { LoginComponent } from './components/LoginRegistration/login/login.component';
import { CaptureformComponent } from './components/leadForms/captureform/captureform.component';
import { ContactformComponent } from './components/leadForms/contactform/contactform.component';
import { ContacttableComponent } from './components/pages/contacttable/contacttable.component';
import { LeaddetailsComponent } from './components/pages/leaddetails/leaddetails.component';
import { LeadformpageComponent } from './components/pages/leadformpage/leadformpage.component';
import { PipelineComponent } from './components/pages/pipeline/pipeline.component';
import { AuthGuard } from './services/auth.guard';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'capturelead', component: CaptureformComponent },
  { path: 'contactlist', component: ContacttableComponent, canActivate: [AuthGuard] },
  { path: 'contact/:leadID', component: ContactformComponent },
  { path: 'leadform/:leadID', component: LeadformpageComponent },
  { path: 'leaddetail/:leadID', component: LeaddetailsComponent, canActivate: [AuthGuard] },
  { path: 'pipeline', component: PipelineComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
