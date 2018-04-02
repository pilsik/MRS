import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from "../../../../shared/services/auth.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class HeaderComponent implements OnInit {

    currentUser: string;

    constructor(private authService: AuthService, private router: Router) {
    }

    ngOnInit() {
        this.currentUser = localStorage.getItem('currentUser');
    }

    logOutFromProfile() {
        this.authService.logOut()
            .subscribe(
                () => {
                    this.router.navigate(['/login']);
                },
                () => {
                    this.router.navigate(['/login']);
                });
    }
}
