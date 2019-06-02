import {Component, OnInit} from '@angular/core';
import {FacultyService} from '../faculty.service';
import {UtilityService} from '../../shared/services/utility.service';
import {AddFeedObject} from '../add-feed/add-feed.component';
import {FeedData, FeedBodyFields} from '../faculty.interface';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  public bodyFields: FeedBodyFields[] = [
    {key: 'title', type: 'input', title: 'Title'},
    {key: 'eventTagline', type: 'input', title: 'Event tagline'},
    {key: 'department', type: 'select', title: 'Department'},
    {key: 'deadlineDate', type: 'date', title: 'Deadline'},
    {key: 'sendNotification', type: 'toggle', title: 'Send notification'},
  ];
  public size: number;
  public currentIndex: number = 0;
  public isReady: boolean = false;
  public eventData: FeedData[] = [];
  public departments: string[] = [];
  public inEditMode: boolean = false;
  public globalError: boolean = false;
  public addFeedObjects: AddFeedObject[] = [
    {label: 'author', key: 'authorId', type: 'authorId'},
    {label: 'title', key: 'title', type: 'input'},
    {label: 'event tagline', key: 'eventTagline', type: 'ckeditor'},
    {label: 'department', key: 'department', type: 'select', options: this.departments},
    {label: 'deadline date', key: 'deadlineDate', type: 'date'},
    {label: 'send notification', key: 'sendNotification', type: 'boolean'},
    {label: 'image', key: 'imageurl', type: 'browse'},
  ];

  private selectedDepartments: string[] = ['RESEARCH,LEARNING,GENERAL'];

  constructor(
    private facultyService: FacultyService,
    public utilityService: UtilityService
  ) {
  }

  ngOnInit() {
    this.getData(true);
  }

  public processFilterChange(selectedFilters: string[]): void {
    this.selectedDepartments = selectedFilters;
    this.isReady = false;
    this.getData();
  }

  private getData(initial: boolean = false): void {
    this.globalError = false;
    forkJoin([
      this.facultyService.getFeed({
        departments: this.selectedDepartments,
        feedType: 'EVENTS',
        index: this.currentIndex,
        size: 10
      }),
      this.facultyService.getDepartments()
    ]).subscribe((dataArray) => {
      this.eventData = dataArray[0].feed;
      this.size = dataArray[0].size;
      if (initial) {
        this.departments = dataArray[1].departments.filter((dep) => {
          return dep !== 'ALL';
        });
        this.addFeedObjects.find((obj) => {
          return obj.key === 'department';
        }).options = this.departments;
        // this.addFeedObjects.find((obj) => {
        //   return obj.key === 'department';
        // }).options.push('ALL');
      }
      this.isReady = true;
    });
  }

  public processSave(saveObject: FeedData): void {
    this.isReady = false;
    this.inEditMode = false;
    this.facultyService.editFeed(saveObject).subscribe(data => {
      this.getData();
    }, error => {
      this.isReady = true;
      this.globalError = true;
    });
  }

  public enterEditMode(action): void {
    this.globalError = false;
    this.inEditMode = action;
  }

  public processDelete(feedId: string): void {
    this.isReady = false;
    this.facultyService.deleteFeed(feedId).subscribe((data) => {
      this.getData();
    });
  }

  public addFeed(postObject): void {
    this.isReady = false;
    this.facultyService.addFeed('Event', postObject).subscribe((data) => {
      this.getData();
    });
  }

  public processPageChangeEvent(page: number): void {
    this.currentIndex = page;
    this.isReady = false;
    this.getData();
  }
}
