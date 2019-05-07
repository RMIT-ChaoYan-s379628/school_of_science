import { Injectable } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import * as moment from 'moment';

@Injectable()
export class UtilityService {

    constructor(private route: ActivatedRoute) {
    }

    public getParam(paramKey: string): string {
        let url = new URL(window.location.href);
        return url.searchParams.get(paramKey);
    }

    public formatDate(timestamp: number): string {
        return moment(timestamp).format("YYYY-MM-DD");
    }

    public isDate(date: string): boolean {
        return moment(date).isValid();
    }
}
