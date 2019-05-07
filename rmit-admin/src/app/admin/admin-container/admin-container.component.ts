import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';

@Component({
    selector: 'app-admin-container',
    templateUrl: './admin-container.component.html',
    styleUrls: ['./admin-container.component.scss']
})
export class AdminContainerComponent implements OnInit {

    constructor(
        private authService: AuthService,
    ) { }

    ngOnInit() {
    }

}
