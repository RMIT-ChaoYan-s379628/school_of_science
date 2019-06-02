import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContactsComponent} from './contacts/contacts.component';
import {UsersComponent} from './users/users.component';
import {RouterModule} from '@angular/router';
import {FacultyContainerComponent} from './faculty-container/faculty-container.component';
import {FacultyService} from './faculty.service';
import {EventsComponent} from './events/events.component';
import {NewsComponent} from './news/news.component';
import {DeadlinesComponent} from './deadlines/deadlines.component';
import {SharedModule} from '../shared/shared.module';
import {FormsModule} from '@angular/forms';
import {FeedComponent} from './feed/feed.component';
import {GlobalErrorComponent} from './global-error/global-error.component';
import {FilterComponent} from './filter/filter.component';
import {AddFeedComponent} from './add-feed/add-feed.component';
import {HttpClientModule} from '@angular/common/http';
import { CKEditorModule } from 'ng2-ckeditor';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    FormsModule,
    HttpClientModule,
    CKEditorModule,
    RouterModule.forChild([
      {
        path: '',
        component: FacultyContainerComponent,
      },
      {
        path: 'events',
        component: EventsComponent,
      },
      {
        path: 'news',
        component: NewsComponent
      },
      {
        path: 'deadlines',
        component: DeadlinesComponent
      },
      {
        path: 'contacts',
        component: ContactsComponent,
      },
      {
        path: 'users',
        component: UsersComponent,
      },
    ]),
  ],
  providers: [
    FacultyService
  ],
  declarations: [
    ContactsComponent,
    UsersComponent,
    FacultyContainerComponent,
    EventsComponent,
    NewsComponent,
    DeadlinesComponent,
    FeedComponent,
    GlobalErrorComponent,
    FilterComponent,
    AddFeedComponent
  ]
})
export class FacultyModule {
}
