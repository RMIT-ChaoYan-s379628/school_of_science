import {Component, OnInit, Input, EventEmitter, Output, ViewChild, ElementRef} from '@angular/core';
import {AuthService} from '../../shared/services/auth.service';
import {FacultyService} from '../faculty.service';

export interface AddFeedObject {
  key: string;
  type: 'input' | 'authorId' | 'date' | 'boolean' | 'select' | 'browse' | 'ckeditor';
  label: string;
  options?: string[];
}

@Component({
  selector: 'app-add-feed',
  templateUrl: './add-feed.component.html',
  styleUrls: ['./add-feed.component.scss']
})
export class AddFeedComponent implements OnInit {

  @Input() public title: string;
  @Input() public feedObjects: AddFeedObject[];

  @Output() public addNewFeed: EventEmitter<object> = new EventEmitter();
  @ViewChild('browse') public browse: ElementRef;
  protected config: any = {
    uiColor: '#F8F8F8',
    language: 'en',
    height: '100',
    toolbarCanCollapse: true,
    toolbar: [['Maximize'], ['Undo', 'Redo', '-', 'Cut', ' Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Link', 'Unlink', 'Anchor', '-', 'Image', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', '-', 'Source'], ['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript', '-', 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', 'Blockquote'], ['Styles', 'Format', 'Font', 'FontSize']]  // 工具部分
  };

  public browseValue;
  public feedObject = {};
  public editMode: boolean = false;

  constructor(
    public authService: AuthService,
    private facultyService: FacultyService
  ) {
  }

  ngOnInit() {
    this.feedObjects.forEach((obj) => {
      this.feedObject[obj.key] = undefined;
      if (obj.type === 'authorId') {
        this.feedObject[obj.key] = this.authService.userId;
      }
    });
  }

  public addFeed(): void {
    this.feedObject['deleted'] = false;
    if (this.browse && this.browseValue) {
      this.facultyService.saveImage(this.browse.nativeElement.files[0]).subscribe((data) => {
        if (data.body) {
          this.feedObject['imageurl'] = JSON.parse(data.body).message;
          this.addNewFeed.emit(this.feedObject);
          this.editMode = false;
        }
      });
    } else {
      this.addNewFeed.emit(this.feedObject);
      this.editMode = false;
    }
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
