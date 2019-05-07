import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {
    
    public get isAuthenticated(): boolean {
        return this.authService.isAuthenticated;
    }
    public get userRole(): string {
        return this.authService.userRole;
    }

    constructor(
        private authService: AuthService,
        private activated: ActivatedRoute
    ) { }

    ngOnInit() {}

    public get currentRoute(): string {
        return this.activated.snapshot['_routerState'].url;
    }
    public logout(): void {
        this.authService.logout();
    }
}
