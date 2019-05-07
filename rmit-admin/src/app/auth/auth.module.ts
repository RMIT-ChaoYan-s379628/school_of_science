import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { AuthMessageComponent } from './auth-message/auth-message.component';
import { SetPasswordComponent } from './set-password/set-password.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        SharedModule,
        RouterModule.forChild([
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'login'
            },
            {
                path: 'login',
                component: LoginComponent,
            },
            {
                path: 'forgotpassword',
                component: ForgotPasswordComponent,
            },
            {
                path: 'resetpassword',
                component: ResetPasswordComponent
            },
            {
                path: 'setpassword',
                component: SetPasswordComponent
            },
            {
                path: 'changepassword',
                component: ChangePasswordComponent
            }
        ])
    ],
    declarations: [
        LoginComponent, 
        ForgotPasswordComponent, 
        ResetPasswordComponent,
        AuthMessageComponent,
        SetPasswordComponent,
        ChangePasswordComponent
    ],
})
export class AuthModule { }
