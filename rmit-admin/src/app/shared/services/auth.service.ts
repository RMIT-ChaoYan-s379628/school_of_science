import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { HttpService } from "./http.service";
import { HttpHeaders } from "@angular/common/http";
import { HttpParams } from "@angular/common/http";
import { UserDetails } from "../shared.interface";
import { Router } from "@angular/router";
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AuthService {

    constructor(
        private httpService: HttpService,
        private router: Router,
        private cookieService: CookieService
    ) {}

    private userDetails: UserDetails;

    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/x-www-form-urlencoded'
        })
    }

    public changePassword(body): Observable<any> {
        return this.httpService.post('changePassword', body);
    }
    
    public get isAuthenticated(): boolean {
        if (!JSON.parse(localStorage.getItem('user'))) {
            return false;
        }
        return true;
    }

    public get userRole(): string {
        if (JSON.parse(localStorage.getItem('user'))) {
            return JSON.parse(localStorage.getItem('user')).role;
        }
        return null;
    }

    public get userId(): string {
        if (JSON.parse(localStorage.getItem('user'))) {
            return JSON.parse(localStorage.getItem('user')).userId;
        }
        return null;
    }

    public setUserDetails(userDetails) {
        this.userDetails = userDetails;
        localStorage.setItem('user', JSON.stringify(this.userDetails));
        this.initialNavigation();
    }

    public authenticate(username: string, password: string): Observable<any> {
        const body = new HttpParams()
            .set('username', username)
            .set('password', password);
        return this.httpService.post(
            'authenticate',
            body.toString(),
            this.httpOptions
        );
    }

    public getAccountDetails(): Observable<UserDetails> {
        return this.httpService.get('security/account');
    }

    public forgotPassword(mail: string): Observable<any> {
        return this.httpService.post('forgotPassword', {mail});
    }

    private initialNavigation(): void {
        if (this.userRole === 'ADMIN') {
            this.router.navigate(['/admin']);
        } else if (this.userRole === 'CLIENT') {
            this.router.navigate(['/faculty']);
        }
    }

    public logout(): void {
        this.httpService.get('logout').subscribe((data) => {
            localStorage.clear();
            this.cookieService.deleteAll('/ ', '/');
            this.router.navigate(['auth/login']);
        })
    }

    public verifyToken(email: string, token: string): Observable<any> {
        return this.httpService.get(
            (`verify?mail=${encodeURIComponent(email)}&token=${token}`)
        );
    }

    public resetPassword(email: string, token: string): Observable<any> {
        return this.httpService.get(
            (`resetPassword?mail=${encodeURIComponent(email)}&token=${token}`)
        );
    }

    public setPassword(mail: string, token: string, password: string): Observable<any> {
        return this.httpService.post(
            'setPassword',
            {
                mail,
                token,
                password
            }
        );
    }
}
