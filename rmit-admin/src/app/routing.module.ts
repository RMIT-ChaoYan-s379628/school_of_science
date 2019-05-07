import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { CanActivateSection } from './shared/services/can-activate.service';

const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: 'auth'
    },
    {
        path: 'auth',
        loadChildren: './auth/auth.module#AuthModule'
    },
    {
        path: 'admin',
        loadChildren: './admin/admin.module#AdminModule',
        canActivate: [CanActivateSection]
    },
    {
        path: 'faculty',
        loadChildren: './faculty/faculty.module#FacultyModule',
        canActivate: [CanActivateSection]
    },
    {
        path: '**',
        component: NotFoundComponent,
    }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forRoot(routes)
    ],
    exports: [RouterModule]
})
export class RoutingModule { }
