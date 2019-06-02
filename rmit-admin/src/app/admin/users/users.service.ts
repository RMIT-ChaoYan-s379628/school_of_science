import {Injectable} from '@angular/core';
import {HttpService} from '../../shared/services/http.service';
import {UserInterface} from '../admin.iterface';
import {Observable, Subject} from 'rxjs';
import {GlobalResponse} from '../../shared/shared.interface';
import {HttpHeaders, HttpParams} from '@angular/common/http';
import {AuthService} from '../../shared/services/auth.service';
import {GetUsersObject} from './user.interface';

@Injectable()
export class UsersService {
  constructor(
    private httpService: HttpService,
    private authService: AuthService
  ) {
  }

  public actionCompleted: Subject<void> = new Subject();

  public httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  public getClients(
    index: number = 0,
    size: number = 10,
  ): Observable<any> {
    return this.httpService.get(
      `engine/admin/getClient?index=${index}&size=${size}`
      // `client/getClients?index=${index}&size=${size}`,
    );
  }

  public deleteClient(userId: string): Observable<any> {
    console.log({userId});
    return this.httpService.post(
      'engine/admin/deleteClient',
      // 'admin/deleteUser',
      {userId},
      this.httpOptions,
    );
  }

  public addClient(userData: UserInterface): Observable<GlobalResponse> {
    return this.httpService.post(
      // 'admin/addClient',
      'engine/admin/addClient',
      userData,
      this.httpOptions
    );
  }

  public disableClient(userId: string): Observable<any> {
    console.log(userId);
    return this.httpService.post(
      'engine/admin/disableClient',
      {userId},
      this.httpOptions,
    );
  }

  public enableClient(userId: string): Observable<any> {
    console.log(userId);
    return this.httpService.post(
      'engine/admin/enableClient',
      {userId},
      this.httpOptions,
    );
  }

  public deleteUser(userId: string[]): void {
    this.httpService.post('admin/deleteUser', {userId}, this.httpOptions);
  }

  public getDepartments(): Observable<any> {
    return this.httpService.get('engine/engine/getDepartments');
  }

  public searchUser(userId: string): Observable<any> {
    return this.httpService.get(`engine/admin/searchClient?user=${userId}`);
  }
}
