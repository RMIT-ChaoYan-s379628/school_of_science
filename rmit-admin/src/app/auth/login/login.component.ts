import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../shared/services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public isLoading: boolean = false;
  public formData = {
    userId: null,
    password: null,
    errors: [],
    errorText: null,
    get isValid() {
      return !this.errors.length;
    },
    validate: function () {
      this.errors = [];
      if (!this.userId) {
        this.errors.push('userId');
        this.errorText = 'Highlighted fields are required';
      } else if (!this.userId.split('@')[1].includes('rmit.edu.au')) {
        this.errors.push('userId');
        this.errorText = 'Only RMIT users are allowed to login';
      }
      if (!this.password) {
        this.errors.push('password');
        this.errorText = 'Highlighted fields are required';
      }
    }
  };
  public hasError: boolean = false;

  public get errorText(): string {
    return this.formData.errorText;
  }

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {
    if (this.authService.isAuthenticated) {
      if (this.authService.userRole === 'ADMIN') {
        this.router.navigate(['admin']);
      } else if (this.authService.userRole === 'CLIENT') {
        this.router.navigate(['faculty']);
      }
    }
  }

  public login(): void {
    this.hasError = false;
    this.formData.validate();
    // this.formData.userId = 'admin@rmit.edu.au';
    // this.formData.password = 'password';
    if (this.formData.isValid) {
      this.isLoading = true;
      this.authService.authenticate(
        this.formData.userId,
        this.formData.password
      ).subscribe((res) => {
          this.authService.getAccountDetails().subscribe(data => {
            this.authService.setUserDetails(data);
          });
        }
        , (err) => {
          this.hasError = true;
          this.formData.errorText = 'User id or password is incorrect';
          this.isLoading = false;
        }
      );
    }
  }

}
