import { Component, OnInit } from '@angular/core';
import { FacultyService } from '../faculty.service';
import { forkJoin, Subscription } from 'rxjs';
import { UserData, FeedBodyFields } from '../faculty.interface';
import { AddFeedObject } from '../add-feed/add-feed.component';

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

    private subscription: Subscription;
    public isSearching: boolean = false;
    public bodyFields: FeedBodyFields[] = [
        { key: 'name', type: 'input', title: 'Name' },
        { key: 'userId', type: 'input', title: 'Email' },
        { key: 'department', type: 'select', title: 'Department' },
    ];
    public headerItems: Object[] = [
        { title: 'Status:', key: 'status' },
        { title: 'Created at:', key: 'createdDate' },
        { title: 'Last updated at:', key: 'updatedDate' }
    ];
    public addFeedObjects: AddFeedObject[] = [
        { label: 'name', key: 'name', type: 'input' },
        { label: 'email', key: 'userId', type: 'input' },
    ];
    public size: number;
    public globalError: boolean = false;
    public isReady: boolean = false;
    public usersData: UserData[] = [];
    public currentIndex: number = 0;
    public departments: string[] = [];
    constructor(
        private facultyService: FacultyService
    ) { }

    ngOnInit() {
        this.getData(true);
    }

    private getData(initial: boolean = false): void {
        forkJoin(
            this.facultyService.getUsers(this.currentIndex, 10),
            this.facultyService.getDepartments()
        ).subscribe(dataArray => {
            this.size = dataArray[0].size;
            this.usersData = dataArray[0].userList;
            if (initial) {
                this.departments = dataArray[1].departments.filter((dep) => {
                    return dep !== 'ALL';
                });
                // this.addFeedObjects.find((obj) => {
                //     return obj.key === 'department'
                // }).options = this.departments;
            }
            this.isReady = true;
            this.isSearching = false;
        })
    }

    public processDisable(userId: string): void {
        this.isReady = false;
        this.facultyService.disableClient([userId]).subscribe(data => {
            this.getData();
        });
    }

    public addUser(userData): void {
        this.isReady = false;
        userData.department = "ALL";
        this.facultyService.addUser([userData]).subscribe((data) => {
            this.getData();
        });
    }

    public processPageChangeEvent(page: number): void {
        this.currentIndex = page;
        this.isReady = false;
        this.getData();
    }

    public search(searchValue: string): void {
        let subscribe = () => {
            this.subscription = this.facultyService.searchUser(searchValue.trim()).subscribe((data) => {
                this.usersData = data.userList;
                this.size = data.userList ? data.userList.length : 0;
                this.isSearching = false;
            });
        }
        this.isSearching = true;
        if (searchValue) {
            if (this.subscription) {
                this.subscription.unsubscribe();
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
