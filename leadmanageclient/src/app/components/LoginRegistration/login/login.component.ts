import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { catchError } from 'rxjs';
import { LoginRequest } from 'src/app/model/loginRequest';
import { AuthService } from 'src/app/services/AuthService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
  loginRequest: LoginRequest = new LoginRequest();

  constructor(private authService: AuthService, private router: Router) { }

  login(): void {
    this.authService.login(this.loginRequest).pipe(
      catchError((error) => {
        console.error('Login failed:', error);
        throw error;
      })
    ).subscribe((response) => {
      const token = response.token;
      this.authService.setToken(token);
      this.router.navigate(["/pipeline"])
    });
  }
}
