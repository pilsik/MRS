import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from "../../../shared/models/user.model";
import {AuthService} from "../../../shared/services/auth.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

  user: User = new User();
  errorMessage: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
  }

  login() {
    this.authService.logIn(this.user)
      .subscribe(() => {
          this.router.navigate(['/system/rigs']);
        }, ()  => {
          this.errorMessage = "error :  Username or password is incorrect";
        }
      )
  }
}
