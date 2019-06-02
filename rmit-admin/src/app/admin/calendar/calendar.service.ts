import { Injectable } from "@angular/core";
import { HttpService } from "../../shared/services/http.service";
import { Observable } from "rxjs";
import { HttpParams, HttpHeaders } from "@angular/common/http";
import { AuthService } from "../../shared/services/auth.service";

@Injectable()
export class CalendarService {
    constructor(
        private http: HttpService,
        private authService: AuthService
    ) {}

    private headers = new HttpHeaders({
      userid: this.authService.userId
    })

    public getCalendars(): Observable<any> {
        return this.http.get('engine/student/getCalender');
    }

    public deleteCalender(event): Observable<any> {
        return this.http.post('engine/admin/deleteCalender', event);
    }

    public setCalender(data): Observable<any> {
        return this.http.post('engine/admin/setCalender', JSON.stringify(data));
    }

    public editCalender(object): Observable<any> {
        return this.http.post('engine/admin/editCalender', object);
    }
}