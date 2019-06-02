import { Component, OnInit } from '@angular/core';
import { FacultyService } from '../faculty.service';

@Component({
    selector: 'app-faculty-container',
    templateUrl: './faculty-container.component.html',
    styleUrls: ['./faculty-container.component.scss']
})
export class FacultyContainerComponent implements OnInit {

    public facultySections = [
        {
            name: 'EVENTS',
            link: 'events',
            buttonText: 'Manage Events'
        },
        {
            name: 'NEWS',
            link: 'news',
            buttonText: 'Manage News'
        },
        {
            name: 'DEADLINES',
            link: 'deadlines',
            buttonText: 'Manage Deadlines'
        },
        {
            name: 'CONTACTS',
            link: 'contacts',
            buttonText: 'Manage Contacts'
        },
        {
            name: 'USERS',
            link: 'users',
            buttonText: 'Manage Users'
        }
    ]

    constructor(
    ) { }

    ngOnInit() {
    }

}
