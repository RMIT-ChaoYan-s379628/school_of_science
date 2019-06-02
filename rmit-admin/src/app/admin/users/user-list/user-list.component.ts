import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {FacultyData} from '../user.interface';
import {UsersService} from '../users.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  @Input() public faculties: FacultyData[];
  @Input() public size: number;

  @Input() public confirmQuestion: string;

  @Output() public actionInProgress: EventEmitter<boolean> = new EventEmitter();
  @Output() public pageChangeEvent: EventEmitter<number> = new EventEmitter();

  public confirmModalDisplayed: boolean = false;
  public itemBeingDeleted: string = null;

  constructor(
    private userService: UsersService
  ) {
  }

  ngOnInit() {
  }

  public disableUser(userId: string): void {
    this.actionInProgress.emit(true);
    this.userService.disableClient(userId).subscribe(data => {
      this.userService.actionCompleted.next();
    });
  }

  public processPageChangeEvent(newPageIndex: number): void {
    this.actionInProgress.emit(true);
    this.pageChangeEvent.emit(newPageIndex);
    this.userService.actionCompleted.next();
  }

  public deleteUser(userId: string): void {
    this.actionInProgress.emit(true);
    this.userService.deleteClient(userId).subscribe(data => {
      this.userService.actionCompleted.next();
    });
  }

  public enableUser(userId: string): void {
    this.actionInProgress.emit(true);
    this.userService.enableClient(userId).subscribe(data => {
      this.userService.actionCompleted.next();
    });
  }

  public openDeleteConfirmation(userId: string): void {
    this.itemBeingDeleted = userId;
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
    this.deleteUser(this.itemBeingDeleted);
  }
}
