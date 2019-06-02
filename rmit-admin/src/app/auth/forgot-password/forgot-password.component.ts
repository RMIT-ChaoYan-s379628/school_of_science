import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';

@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
    
    public messageState: boolean = false;
    public email: string = null;
    public error: 'required' | 'invalid' = null;
    public errorText: string;
    
    constructor(
        private authService: AuthService
    ) { }
    
    ngOnInit() {
    }
    
    public setError(errorType: 'required' | 'invalid'): void {
        this.error = errorType;
        if (this.error === 'required') {
            this.errorText = 'Please enter your user id or email';
        } else {
            this.errorText = `There is no user with ${this.email} registered`;
        }
    }

    public reset(): void {
        this.error = null;
        if (this.email) {
            this.authService.forgotPassword(this.email).subscribe((res) => {
                if (res.code === 0) {
                    this.messageState = true;
                }
            })
        } else {
            this.setError('required');
        }

    }
}
