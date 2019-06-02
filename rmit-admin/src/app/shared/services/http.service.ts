import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_URL } from '../../app.config';

@Injectable()
export class HttpService {

    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    }

    public get(endpoint: string, params: HttpParams = null, headers: HttpHeaders = null): Observable<any> {
        return this.http.get(SERVER_URL + endpoint, { params: params, headers: headers });
    }

    public post(endpoint: string, body: any, httpOptions = this.httpOptions): Observable<any> {
        return this.http.post(SERVER_URL + endpoint, body, httpOptions);
    }

    public request(req): Observable<any> {
        return this.http.request(req);
    }

    constructor(private http: HttpClient) { }
}
