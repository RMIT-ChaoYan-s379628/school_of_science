import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

interface FilterData {
    name: string;
    isSelected: boolean;
}

@Component({
    selector: 'app-filter',
    templateUrl: './filter.component.html',
    styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

  @Input() public inEditMode: boolean;
    @Input() public set filtersData(filtersData: string[]) {
        this.filters = filtersData.map((filterName: string) => {
            return {
                name: filterName,
                isSelected: true,
            }
        })
    };

    @Output() public filterChange: EventEmitter<string[]> = new EventEmitter();

    public filters: FilterData[];

    public get selectedFiltersCount(): number {
        return this.filters.filter((dep: FilterData) => {
            return dep.isSelected;
        }).length;
    }

    constructor() { }

    ngOnInit() {
    }

    public filterSelect(filterObject: FilterData) {
        const selectedFiltersCount = this.selectedFiltersCount;
        if (selectedFiltersCount > 1 && filterObject.isSelected) {
            filterObject.isSelected = !filterObject.isSelected;
        } else if (!filterObject.isSelected) {
            filterObject.isSelected = !filterObject.isSelected;
        }
        if (selectedFiltersCount !== this.selectedFiltersCount) {
            const selectedFilters: string[] = this.filters.filter((filter) => {
                return filter.isSelected
            }).map((filter) => {
                return filter.name;
            });
            this.filterChange.emit(selectedFilters);
        }
    }
}
