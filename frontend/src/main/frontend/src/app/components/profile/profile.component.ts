import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
  currentUser: string;
  closeResult: string;

  constructor(public authService: AuthService, public router: Router,private modalService: NgbModal) {
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

  open(content) {
    this.modalService.open(content).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed `;
    });
  }

}
