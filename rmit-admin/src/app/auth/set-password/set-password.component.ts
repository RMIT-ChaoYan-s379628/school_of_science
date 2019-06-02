import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { UtilityService } from '../../shared/services/utility.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-set-password',
  templateUrl: './set-password.component.html',
  styleUrls: ['./set-password.component.scss']
})
export class SetPasswordComponent implements OnInit {

    public isLoading: boolean = false;
    public hasError: boolean = false;
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
    private _errorText: string = 'Both fields are required and they should match';

    public get errorText(): string {
        return this._errorText;
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

    private verifyToken(): void {
        this.isLoading = true;
        this.authService.verifyToken(
            this.utilityService.getParam('mail'), 
            this.utilityService.getParam('token')
        ).subscribe(data => {
            if (data.code !== 0) {
                this.hasError = true;
                this._errorText = data.message;
                this.isLoading = false;
            } else {
                this.authService.setPassword(
                    this.utilityService.getParam('mail'),
                    this.utilityService.getParam('token'),
                    this.formData.password
                ).subscribe(data => {
                    if (data.code === 0) {
                        this.isLoading = false;
                        this.messageState = true;
                    } else {
                        this.isLoading = false;
                        this.hasError = true;
                        this._errorText = data.message;
                    }
                })
            }
        });
    }

    public set(): void {
        this.formData.validate();
        if (this.formData.isValid) {
            this.verifyToken();
        }
    }

}
