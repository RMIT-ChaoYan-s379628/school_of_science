import {Component, OnInit} from '@angular/core';
import {FacultyService} from '../faculty.service';
import {ContactData, FeedBodyFields} from '../faculty.interface';
import {forkJoin} from 'rxjs';
import {AddFeedObject} from '../add-feed/add-feed.component';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})
export class ContactsComponent implements OnInit {


  public bodyFields: FeedBodyFields[] = [
    {key: 'name', type: 'input', title: 'Name'},
    {key: 'phoneNo', type: 'input', title: 'Phone Number'},
    {key: 'emailId', type: 'input', title: 'Email'},
    {key: 'department', type: 'select', title: 'department'},
  ];
  public headerItems: Object[] = [
    {title: 'Created at:', key: 'createdDate'},
    {title: 'Last updated at:', key: 'updatedDate'}
  ];
  public inEditMode: boolean = false;
  public isReady: boolean = false;
  public size: number;
  public departments: string[] = [];
  public currentIndex: number = 0;
  public contactData: ContactData[] = [];
  public selectedDepartments: string[] = [''];
  public globalError: boolean = false;
  public addContactObjects: AddFeedObject[] = [
    {key: 'name', type: 'input', label: 'Name'},
    {key: 'phoneNo', type: 'input', label: 'Phone Number'},
    {key: 'emailId', type: 'input', label: 'Email'},
    {key: 'department', type: 'select', label: 'Department', options: this.departments},
  ];

  constructor(
    private facultyService: FacultyService
  ) {
  }

  ngOnInit() {
    this.getData(true);
  }

  private getData(initial: boolean = false): void {
    forkJoin([
      this.facultyService.getContacts(this.currentIndex, 10, this.selectedDepartments),
      this.facultyService.getDepartments()
    ]).subscribe((dataArray) => {
      this.contactData = dataArray[0].contacts;
      this.size = dataArray[0].size;
      if (initial) {
        this.departments = dataArray[1].departments.filter((dep) => {
          return dep !== 'ALL';
        });
        this.addContactObjects.find((obj) => {
          return obj.key === 'department';
        }).options = this.departments;
      }
      this.isReady = true;
    });
  }

  public processFilterChange(selectedFilters: string[]): void {
    this.selectedDepartments = selectedFilters;
    this.isReady = false;
    this.getData();
  }

  public enterEditMode(action): void {
    this.globalError = false;
    this.inEditMode = action;
  }

  public processSave(saveObject: ContactData): void {
    this.isReady = false;
    this.inEditMode = false;
    this.facultyService.editContact(saveObject).subscribe(data => {
      this.getData();
    }, error => {
      this.isReady = true;
      this.globalError = true;
    });
  }

  public processDelete(feedId: string): void {
    this.isReady = false;
    this.facultyService.deleteContact(feedId).subscribe((data) => {
      this.getData();
    });
  }

  public addContact(postObject): void {
    this.isReady = false;
    this.facultyService.addContact(postObject).subscribe((data) => {
      this.getData();
    });
  }

  public processPageChangeEvent(page: number): void {
    this.currentIndex = page;
    this.isReady = false;
    this.getData();
  }
}
