import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
  currentUser: string;

  constructor(public authService: AuthService, public router: Router) {
    this.currentUser = localStorage.getItem('currentUser');
  }

  ngOnInit() {
  }

// login out from the app
  logOutFromProfile() {
    this.authService.logOut()
      .subscribe(
        data => {
          this.router.navigate(['/login']);
        },
        error => {

        });
  }
}
