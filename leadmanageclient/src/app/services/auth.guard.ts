import { Injectable } from '@angular/core';
import {
    UrlTree,
    Router,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './AuthService';


@Injectable({
    providedIn: 'root',
})
export class AuthGuard {
    constructor(public authService: AuthService, public router: Router) { }
    canActivate(
    ):
        | Observable<boolean | UrlTree>
        | Promise<boolean | UrlTree>
        | boolean
        | UrlTree {

        if (this.authService.isLoggedIn !== true) {
            window.alert('Access not allowed!');
            this.router.navigate(['login']);
        }
        return true;
    }
}