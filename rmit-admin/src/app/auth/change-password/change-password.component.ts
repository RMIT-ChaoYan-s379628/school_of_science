import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';

@Component({
    selector: 'app-change-password',
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
    
    public messageState: boolean = false;
    public error: boolean = false;
    public errors: string[] = [];
    public _errorText: string = '';
    public formValue = {
        current: null,
        password: null,
        repeat: null,
    };
    public get errorText(): string {
        return this._errorText;
    }
    constructor(
        private authService: AuthService
    ) { }
    
    ngOnInit() {
    }

    public validate(): void {
        this.error = false;
        this.errors = [];
        if (!this.formValue.current) {
            this.errors.push('current');
            this._errorText = 'Enter your current password';
        } else if (!this.formValue.password || this.formValue.password !== this.formValue.repeat) {
            this.errors.push('password');
            this.errors.push('repeat');
            this._errorText = 'New passwords should match';
        }
    }

    public change(): void {
        this.validate();
        if (this.errors.length) {
            this.error = true;
        } else {
            this.authService.changePassword({
                oldPassword: this.formValue.current,
                newPassword: this.formValue.password
            }).subscribe(data => {
                if (data.code === 0) {
                    this.messageState = true;
                    this.authService.logout();
                } else {
                    this.error = true;
                    this._errorText = data.message;
                }
            });
        }
    }

}
