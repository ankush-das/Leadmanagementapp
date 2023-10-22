import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SignUpRequest } from '../model/SignUpRequest';

@Injectable({
    providedIn: 'root',
})
export class UserService {
    private registrationApiUrl = 'http://localhost:8080/api/auth/signup';

    constructor(private http: HttpClient) { }

    registerUser(signUpRequest: SignUpRequest): Observable<any> {
        return new Observable(observer => {
            this.http.post(this.registrationApiUrl, signUpRequest, { responseType: 'text' }).subscribe({
                next: response => {
                    console.log('Registration successful:', response);
                    observer.next(response);
                },
                error: error => {
                    console.error('Registration failed:', error);
                    observer.error(error);
                },
                complete: () => {
                    observer.complete();
                }
            });
        });
    }
}
