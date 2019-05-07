import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate, Router } from "@angular/router";
import { Observable, of } from "rxjs";
import { AuthService } from "./auth.service";

@Injectable()
export class CanActivateSection implements CanActivate {

    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> {
        if (!this.authService.isAuthenticated) {
            this.router.navigate(['auth/login']);
            return of(false);
        } else {
            if (
                this.authService.userRole === 'ADMIN' 
                && route.routeConfig.path === 'admin'
            ) {
                return of(true);
            } else if (
                this.authService.userRole === 'CLIENT'
                && route.routeConfig.path === 'faculty'
            ) {
                return of(true);
            }
        }
        this.router.navigate(['auth/login']);
        return of(false);
    }
}
