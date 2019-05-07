import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpRequest, HttpHeaders, HttpParams, HttpInterceptor, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { tap } from 'rxjs/operators'
import { AuthService } from './shared/services/auth.service';

@Injectable()
export class CustomInterceptor implements HttpInterceptor {

    constructor(private authService: AuthService) {
        
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        request = request.clone({
            withCredentials: true
        });

        return next.handle(request).pipe((tap(
            (event: HttpEvent<any>) => { }, (err: any) => {
                if (err instanceof HttpErrorResponse) {
                    this.authService.logout();
                }
            }
        )));
    }
}
