import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'app-pagination',
    templateUrl: './pagination.component.html',
    styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

    public currentActivePageIndex: number = 0;
    public pagesCount: Array<any> = [];

    @Input() public displayOnPage: number = 10;
    @Input() public size: number;

    @Output() public pageChangeEvent: EventEmitter<number> = new EventEmitter();

    constructor() { }

    ngOnInit() {
        this.pagesCount = new Array(Math.ceil(this.size/this.displayOnPage));
    }

    public processChange(index: number): void {
        if (index !== this.currentActivePageIndex) {
            this.pageChangeEvent.emit(index);
            this.currentActivePageIndex = index;
        }
    }

}
