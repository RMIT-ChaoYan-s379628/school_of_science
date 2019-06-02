import {Injectable} from '@angular/core';
import {HttpService} from '../shared/services/http.service';
import {Observable} from 'rxjs';
import {FeedData, ContactData} from './faculty.interface';
import {HttpHeaders, HttpRequest} from '@angular/common/http';
import {SERVER_URL} from '../app.config';

@Injectable()
export class FacultyService {

  constructor(
    private httpService: HttpService
  ) {
  }

  public getFeed(details: {
    departments: string[];
    feedType: 'NEWS' | 'DEADLINES' | 'EVENTS';
    index: number;
    size: number;
  }): Observable<any> {
    let url: string = 'engine/student/getFeeds?';
    details.departments.forEach((dep: string) => {
      url += `department=${dep}&`;
      console.log(dep);
    });
    url += `feedType=${details.feedType}&`;
    url += `index=${details.index}&`;
    url += `size=${details.size}`;
    return this.httpService.get(url);
  }

  public getDepartments(): Observable<any> {
    return this.httpService.get('engine/engine/getDepartments');
  }

  public editFeed(feedObject: FeedData): Observable<any> {
    return this.httpService.post('engine/faculty/editFeed', feedObject);
  }

  public deleteFeed(feedId: string): Observable<any> {
    return this.httpService.post('engine/faculty/deleteFeed', feedId);
  }

  public addFeed(type: 'DeadLine' | 'Event' | 'News', postObject): Observable<any> {
    return this.httpService.post(`engine/faculty/add${type}`, postObject);
  }

  public getContacts(
    index: number = 0,
    size: number = 10,
    departments: string[]
  ): Observable<any> {
    let url: string = 'engine/student/getContacts?';
    departments.forEach((dep: string) => {
      url += `department=${dep}&`;
    });
    url += `index=${index}&`;
    url += `size=${size}`;
    return this.httpService.get(url);
  }

  public editContact(postObject: ContactData): Observable<any> {
    return this.httpService.post('engine/faculty/editContact', postObject);
  }

  public deleteContact(contactId: string): Observable<any> {
    return this.httpService.post('engine/faculty/deleteContact', contactId);
  }

  public addContact(postObject): Observable<any> {
    return this.httpService.post('engine/faculty/addContact', postObject);
  }

  public getUsers(index: number = 0, size: number = 10): Observable<any> {
    return this.httpService.get(`engine/faculty/getUsers?index=${index}&size=${size}`);
  }

  public disableClient(userIds: string[]): Observable<any> {
    return this.httpService.post('client/disableUsers', {userIds});
  }

  public addUser(users: any[]): Observable<any> {
    return this.httpService.post('client/addUsers', {users});
  }

  public saveImage(formData): Observable<any> {
    const fd: FormData = new FormData();
    fd.append('file', formData);
    const req = new HttpRequest('POST', SERVER_URL + 'engine/api/file/upload', fd, {
      reportProgress: false,
      responseType: 'text'
    });
    return (this.httpService.request(req));
  }

  public searchUser(userEmail: string): Observable<any> {
    return this.httpService.get(`engine/faculty/searchUser?user=${userEmail}`);
  }
}
