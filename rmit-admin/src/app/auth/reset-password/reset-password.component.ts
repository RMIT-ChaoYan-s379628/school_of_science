import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { UtilityService } from '../../shared/services/utility.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-reset-password',
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

    public messageState: boolean = false;
    public formData = {
        password: null,
        repeat: null,
        errors: [],
        get isValid() {
            return this.password && (this.password === this.repeat)
        },
        validate: () => {
            this.formData.errors = [];
            if (!this.formData.isValid) {
                this.formData.errors.push('invalid');
            }
        }
    }

    public get errorText(): string {
        return 'Both fields are required and they should match';
    }

    constructor(
        private authService: AuthService,
        private utilityService: UtilityService,
        private router: Router
    ) { }

    ngOnInit() {
        this.checkParams();
    }

    private checkParams(): void {
        if (!this.utilityService.getParam('mail') || !this.utilityService.getParam('token')) {
            this.router.navigate(['auth/login']);
        }
    }

    public verifyToken(): void {
        this.authService.resetPassword(
            this.utilityService.getParam('mail'),
            this.utilityService.getParam('token')
        ).subscribe(data => {
            if (data.code !== 0) {
                
            } else {
                this.authService.setPassword(
                    this.utilityService.getParam('mail'),
                    this.utilityService.getParam('token'),
                    this.formData.password
                ).subscribe(data => {
                    if (data.code === 0) {
                        this.messageState = true;
                    }
                })
            }
        });
    }

    public reset(): void {
        this.formData.validate();
        if (this.formData.isValid) {
            this.verifyToken();
        }
    }
}
