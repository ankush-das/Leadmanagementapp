import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SignUpRequest } from 'src/app/model/SignUpRequest';
import { UserService } from 'src/app/services/UserService';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  signUpRequest: SignUpRequest = new SignUpRequest();
  initialSignUpRequest: SignUpRequest = new SignUpRequest();

  constructor(private userService: UserService, private router: Router) { }

  registerUser(signUpData: SignUpRequest): void {
    this.userService.registerUser(signUpData).subscribe({
      next: (response) => {
        console.log('Registration successful:', response);
        this.signUpRequest = { ...this.initialSignUpRequest };
      },
      error: (error) => {
        console.error('Registration failed:', error);
      }
    });
  }
}
