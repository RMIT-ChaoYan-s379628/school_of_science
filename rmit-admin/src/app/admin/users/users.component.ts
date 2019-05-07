import {Component, OnInit} from '@angular/core';
import {UsersService} from './users.service';
import {GetUsersObject, FacultyData} from './user.interface';
import {forkJoin, Subscription} from 'rxjs';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  public isSearching: boolean = false;
  public currentIndex: number = 0;
  public currentSize: number;
  public isReady: boolean = false;
  public isLoading: boolean = false;
  public faculties: FacultyData[];
  public departments: string[];
  public subscriber: Subscription;


  constructor(
    private userService: UsersService
  ) {
  }

  ngOnInit() {
    this.getData();
    this.userService.actionCompleted.subscribe((data) => {
      this.getData();
    });
  }

  public getData(): void {
    forkJoin([
      this.userService.getClients(this.currentIndex),
      this.userService.getDepartments()
    ]).subscribe((dataArr) => {
      this.faculties = dataArr[0].facultyList;
      this.currentSize = dataArr[0].size;
      this.departments = dataArr[1].departments;
      this.isReady = true;
      this.isSearching = false;
      this.isLoading = false;
    });
  }
  // public getData(): void {
  //   forkJoin([
  //     this.userService.getClients(this.currentIndex),
  //     this.userService.getDepartments()
  //   ]).subscribe((dataArr) => {
  //     this.departments = dataArr[1].department;
  //     this.faculties = dataArr[0].userList;
  //     this.currentSize = dataArr[0].size;
  //     this.isReady = true;
  //     this.isSearching = false;
  //     this.isLoading = false;
  //   });
  // }

  public processAction(): void {
    this.isLoading = true;
  }

  public processPageChangeRequest(newPageIndex: number): void {
    this.currentIndex = newPageIndex;
  }

  public search(searchValue): void {
    let subscribe = () => {
      this.subscriber = this.userService.searchUser(searchValue.trim()).subscribe(data => {
        this.faculties = data.facultyList;
        this.currentSize = data.facultyList ? data.facultyList.length : 0;
        this.isSearching = false;
      });
    };
    this.isSearching = true;
    if (searchValue) {
      if (this.subscriber) {
        this.subscriber.unsubscribe();
        subscribe();
      } else {
        subscribe();
      }
    } else {
      this.currentIndex = 0;
      this.getData();
    }
  }
}
