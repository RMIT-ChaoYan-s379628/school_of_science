import { Component, OnInit, Output, EventEmitter, ViewChild, ElementRef, Input } from '@angular/core';
import { fromEvent } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

    @Input() public placeholder: string = null;

    @Output() public doSearch: EventEmitter<string> = new EventEmitter();
    @Output() public enterSearchMode: EventEmitter<boolean> = new EventEmitter();
    @ViewChild('searchField') public searchField: ElementRef;
    private searchValue: string = null;

    constructor() { }

    ngOnInit() {
        this.searchChange();
    }

    public searchChange(): void {
        let subscription = fromEvent(
            this.searchField.nativeElement, 'input'
        ).pipe(debounceTime(1000));

        subscription.subscribe((value) => {
            this.searchValue = this.searchField.nativeElement.value;
            this.doSearch.emit(this.searchValue);
        })

    }
}
