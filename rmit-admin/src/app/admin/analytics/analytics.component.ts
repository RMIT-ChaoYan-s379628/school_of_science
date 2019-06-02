import { Component, OnInit } from '@angular/core';
import { AnalyticsService } from './analytics.service';
import { forkJoin } from 'rxjs';

interface AnalyticsData {
    key: string;
    value: any;
}

@Component({
    selector: 'app-analytics',
    templateUrl: './analytics.component.html',
    styleUrls: ['./analytics.component.scss']
})
export class AnalyticsComponent implements OnInit {

    public analyticsData: Array<AnalyticsData> = [];
    public isReady: boolean = false;
    public chartColors: string[] = ['#ffa600', '#003f5c', '#ff6361', '#247BA0', '#F25F5C'];
    public activeAdmins: number;
    public activeUsers: number;
    constructor(
        private analyticsService: AnalyticsService
    ) { }

    ngOnInit() {
        this.getData();
    }

    private getData(): void {
        forkJoin([
            this.analyticsService.getAnalytics(),
            this.analyticsService.getActiveAdmins(),
            this.analyticsService.getActiveUsers()
        ]).subscribe(dataArray => {
            this.processData(dataArray[0].data);
            this.activeAdmins = dataArray[1].active;
            this.activeUsers = dataArray[2].active;
        });
    }

    private processData(data: Object): void {
        this.analyticsData = Object.keys(data).map((key: string) => {
            return {key, value: data[key]};
        });
        this.isReady = true;
    }

}
