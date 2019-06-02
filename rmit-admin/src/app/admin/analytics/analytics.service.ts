import { Injectable } from "@angular/core";
import { HttpService } from "../../shared/services/http.service";
import { Observable } from "rxjs";

@Injectable()
export class AnalyticsService {
    constructor(
        private httpService: HttpService,
    ) {}

    public getAnalytics(): Observable<any> {
        return this.httpService.get('engine/admin/analytics/getFeedDetails');
    }

    public getActiveAdmins(): Observable<any> {
        return this.httpService.get('engine/admin/analytics/getActiveAdmins');
    }

    public getActiveUsers(): Observable<any> {
        return this.httpService.get('engine/admin/analytics/getActiveUsers');
    }
}