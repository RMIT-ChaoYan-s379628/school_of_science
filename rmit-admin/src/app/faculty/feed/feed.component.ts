import {Component, OnInit, Output, Input, EventEmitter, ViewChild, ElementRef} from '@angular/core';
import {FeedData, ContactData} from '../faculty.interface';
import {UtilityService} from '../../shared/services/utility.service';
import {ChangeDetectorRef} from '@angular/core';
import {FacultyService} from '../faculty.service';
import {HttpClient, HttpRequest} from '@angular/common/http';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent implements OnInit {

  @Input() public hasImage: boolean = false;
  @Input() public feedItems: any[] = [];
  @Input() public departments: string[] = [];
  @Input() public type: string = 'feed';
  @Input() public itemRemoveMessage: string = 'This feed has been removed!';
  @Input() public secondaryButtonName: string;
  @Input() public confirmQuestion: string;
  @Input() public hasEdit: boolean = true;
  @Output() public saveObject: EventEmitter<FeedData> = new EventEmitter();
  @Output() public editMode: EventEmitter<boolean> = new EventEmitter();
  @Output() public deleteFeed: EventEmitter<string> = new EventEmitter();

  @Input() public headerItems: Object[] = [
    {title: 'Author:', key: 'authorId'},
    {title: 'Created at:', key: 'createdDate'},
    {title: 'Last updated at:', key: 'updatedDate'}
  ];
  @Input() public bodyFields: { key: string; type: any; title: string }[] = [
    {key: 'title', type: 'input', title: 'Title'},
    {key: 'news', type: 'input', title: 'News'},
    {key: 'eventTagline', type: 'input', title: 'Event tagline'},
    {key: 'department', type: 'select', title: 'Department'},
    {key: 'deadlineDate', type: 'date', title: 'Deadline'},
    {key: 'sendNotification', type: 'toggle', title: 'Send notification'},
  ];

  @ViewChild('browse') public browse: ElementRef;

  public currentEditingFeed: any = null;
  public currentEditingFeedId: string = null;
  public confirmModalDisplayed: boolean = false;
  public itemBeingDeleted: string = null;
  public browseValue;

  constructor(
    public utilityService: UtilityService,
    public facultyService: FacultyService,
    private http: HttpClient
  ) {
  }

  ngOnInit() {
  }

  public enterEditMode(feedId: string): void {
    this.currentEditingFeed = Object.assign({}, this.feedItems.find((feed) => {
      return (feed.feedId || feed.contactId || feed.userId) === feedId;
    }));
    const formatted = this.utilityService.formatDate(this.currentEditingFeed.deadlineDate);
    if (this.type === 'feed') {
      if (this.utilityService.isDate(formatted)) {
        this.currentEditingFeed.deadlineDate = this.utilityService.formatDate(this.currentEditingFeed.deadlineDate);
      } else {
        this.currentEditingFeed.deadlineDate = null;
      }
    }
    this.currentEditingFeedId = feedId;
    this.editMode.emit(true);
  }

  public save(): void {
    if (this.hasImage && this.browse.nativeElement.value) {
      this.facultyService.saveImage(this.browse.nativeElement.files[0]).subscribe((data) => {
        if (data.body) {
          this.currentEditingFeed.imageurl = JSON.parse(data.body).message;
          this.saveObject.emit(this.currentEditingFeed);
        }
      });
    } else {
      this.saveObject.emit(this.currentEditingFeed);
    }
  }

  public cancelEdit(): void {
    if (this.hasImage) {
      this.browse.nativeElement.value = '';
      this.inputChange();
    }
    this.currentEditingFeedId = null;
    this.editMode.emit(false);
  }

  public openDeleteConfirmation(feedId: string): void {
    this.itemBeingDeleted = feedId;
    document.body.classList.add('no-scroll');
    this.confirmModalDisplayed = true;
  }

  public processModalAction(action: string): void {
    if (action === 'close') {
      this.confirmModalDisplayed = false;
      document.body.classList.remove('no-scroll');
    }
  }

  public emitDelete(): void {
    this.confirmModalDisplayed = false;
    this.deleteFeed.emit(this.itemBeingDeleted);
  }

  public openUpload(): void {
    this.browse.nativeElement.click();
  }

  public inputChange(): void {
    if (this.browse) {
      this.browseValue = this.browse.nativeElement.value;
    }
  }
}
