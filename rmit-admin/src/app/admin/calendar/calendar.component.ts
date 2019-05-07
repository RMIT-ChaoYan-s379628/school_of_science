import { Component, OnInit } from '@angular/core';
import { CalendarService } from './calendar.service';
import { UtilityService } from '../../shared/services/utility.service';

@Component({
    selector: 'app-calendar',
    templateUrl: './calendar.component.html',
    styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

    public isReady: boolean = false;
    public calendarData: {
        name: string;
        date: string;
    }[] = [];
    public currentEditingItem: {
        name: string;
        date: string;
    } = null;
    public currentEditingIndex: number = null;
    public addingCalendar = {
        name: null,
        date: null,
        dateError: false,
        nameError: false,
    }

    constructor(
        private calendarService: CalendarService,
        private utilityService: UtilityService,
    ) { }

    ngOnInit() {
        this.getData();
    }

    private getData(): void {
        this.calendarService.getCalendars().subscribe(data => {
            this.calendarData = Object.keys(data.calender).map(key => {
                return { name: key, date: this.utilityService.formatDate(data.calender[key]) };
            });
            this.isReady = true;
        });
    }

    public enterEditMode(index: number): void {
        this.currentEditingItem = Object.assign({}, this.calendarData[index]);
        this.currentEditingIndex = index;
    }

    public cancelEditing(): void {
        this.currentEditingIndex = null;
    }

    public saveEdit(): void {
        this.isReady = false;
        this.calendarService.editCalender({
            event: this.currentEditingItem.name,
            date: this.currentEditingItem.date,
        }).subscribe(data => {
            this.currentEditingIndex = null;
            this.getData();
        })
    }

    public delete(index: number): void {
        this.isReady = false;
        this.calendarService.deleteCalender(this.calendarData[index].name).subscribe(data => {
            this.getData();
        });
    }

    public add(): void {
        this.addingCalendar.nameError = false;
        this.addingCalendar.dateError = false;
        if (!this.addingCalendar.name) {
            this.addingCalendar.nameError = true;
        } if (!this.utilityService.isDate(this.addingCalendar.date)) {
            this.addingCalendar.dateError = true;
        } 
        
        if (!this.addingCalendar.dateError && !this.addingCalendar.nameError) {
            this.isReady = false;
            this.calendarService.setCalender(
                {
                    calender: {
                        [this.addingCalendar.name]: this.addingCalendar.date
                    }
                }
            ).subscribe(data => {
                this.addingCalendar.name = null;
                this.addingCalendar.date = null;
                this.getData();
            });
        }
    }
}
