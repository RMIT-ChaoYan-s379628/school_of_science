import {Component, OnInit, Output, EventEmitter, Input} from '@angular/core';
import {UserForm} from '../../admin.iterface';
import {ResponseCodes, GlobalResponse, EmailRegExp} from '../../../shared/shared.interface';
import {UsersService} from '../users.service';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {

  @Input() public departments: string[];

  @Output() public actionInProgress: EventEmitter<boolean> = new EventEmitter();

  private _errorText: string = 'Fill all required fields with valid information';

  constructor(
    private userService: UsersService
  ) {
  }

  ngOnInit() {
  }

  public get errorText(): string {
    return this._errorText;
  }

  public isErrorDisplayed: boolean = false;
  public state: 'formView' | 'addedView' = 'formView';
  public formData: UserForm = {
    errors: [],
    data: {
      department: 'ALL',
      name: null,
      userId: null
    },
    get isValid() {
      return !this.errors.length;
    }
  };

  public createUser(): void {
    this.isErrorDisplayed = false;
    this.validate();
    if (this.formData.isValid) {
      this.actionInProgress.emit(true);
      this.userService.addClient(this.formData.data).subscribe(
        (res: GlobalResponse) => {
          if (res.code === 0) {
            this.state = 'addedView';
            this.userService.actionCompleted.next();
          } else {
            this.userService.actionCompleted.next();
            this.isErrorDisplayed = true;
            this._errorText = res.message;
          }
        }
      );
    } else {
      this.isErrorDisplayed = true;
    }
  }

  public addAnotherUser(): void {
    Object.keys(this.formData.data).map((key: string) => {
      this.formData.data[key] = null;
    });
    this.state = 'formView';
  }

  private validate(): void {
    this.formData.errors = Object.entries(this.formData.data).filter((entry) => {
      return !entry[1];
    }).map((entry) => {
      return entry[0];
    });
    if (!this.formData.errors.length) {
      if (!EmailRegExp.test(this.formData.data.userId)) {
        this.formData.errors.push('userId');
        this._errorText = 'Enter a valid email address';
      }
    } else {
      this._errorText = 'Fill all required fields with valid information';
    }
  }
}
