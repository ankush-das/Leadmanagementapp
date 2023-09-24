import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../AuthService';
import { User } from '../user';
import { catchError } from 'rxjs';

@Component({
  selector: 'app-loginpage',
  templateUrl: './loginpage.component.html',
  styleUrls: ['./loginpage.component.css']
})
export class LoginpageComponent {

  constructor(private authService: AuthService, private router: Router) { }
  user: User = {
    username: '',
    password: ''
  };

  login(): void {
    this.authService.login(this.user).pipe(
      catchError((error) => {
        console.error('Login failed:', error);
        throw error;
      })
    ).subscribe((response) => {
      const token = response.token;
      this.authService.setToken(token);

      const userRole = response.userRole;
      this.authService.setUserRole(userRole);
      this.router.navigate(['/home']);
    });
  }
}
