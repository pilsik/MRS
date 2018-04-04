import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from "@angular/router";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";

import {User} from "../../../shared/models/user.model";
import {AuthService} from "../../../shared/services/auth.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

    user: User = new User();
    errorMessage: string;

    validatingForm: FormGroup;

    constructor(private authService: AuthService, private router: Router,  private fb: FormBuilder) {
        this.validatingForm = this.fb.group({
            'controlUsername': [null, [Validators.required]],
            'controlPassword': [null, [Validators.required]],
        });
    }

    ngOnInit() {
    }

    login() {
        this.authService.logIn(this.user)
            .subscribe(() => {
                    this.router.navigate(['/system/rigs']);
                }, () => {
                    this.errorMessage = "error :  Username or password is incorrect";
                }
            )
    }
}
