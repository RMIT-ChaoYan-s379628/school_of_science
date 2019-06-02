import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersComponent } from './users/users.component';
import { RouterModule } from '@angular/router';
import { AdminContainerComponent } from './admin-container/admin-container.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { SharedModule } from '../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { UserFormComponent } from './users/user-form/user-form.component';
import { UsersService } from './users/users.service';
import { UserListComponent } from './users/user-list/user-list.component';
import { AnalyticsService } from './analytics/analytics.service';
import { CalendarComponent } from './calendar/calendar.component';
import { CalendarService } from './calendar/calendar.service';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    FormsModule,
    RouterModule.forChild([
      {
        path: '',
        component: AdminContainerComponent
      },
      {
        path: 'users',
        component: UsersComponent,
      },
      {
        path: 'analytics',
        component: AnalyticsComponent
      },
      {
        path: 'calendars',
        component: CalendarComponent
      }
    ])
  ],
  declarations: [
    UsersComponent,
    AdminContainerComponent,
    AnalyticsComponent,
    UserFormComponent,
    UserListComponent,
    CalendarComponent
  ],
  exports: [
    AdminContainerComponent
  ],
  providers: [
    UsersService,
    AnalyticsService,
    CalendarService
  ]
})
export class AdminModule { }
