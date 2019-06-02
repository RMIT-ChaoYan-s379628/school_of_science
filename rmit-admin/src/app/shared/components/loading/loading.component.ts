import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
    selector: 'app-loading',
    templateUrl: './loading.component.html',
    styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnInit, OnDestroy {

    constructor() { }

    ngOnInit() {
        document.body.classList.add('no-scroll');
    }

    ngOnDestroy(): void {
        document.body.classList.remove('no-scroll');
    }
}
