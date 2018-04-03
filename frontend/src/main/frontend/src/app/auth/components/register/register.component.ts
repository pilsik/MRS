import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from "../../../shared/models/user.model";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";
import {FormControl, FormGroup, FormBuilder, Validators} from "@angular/forms";
import {Observable} from "rxjs/Observable";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {

    user: User = new User();
    errorMessage: string;
    validatingForm: FormGroup;

    constructor(public accountService: AccountService, public router: Router, private fb: FormBuilder) {
        this.validatingForm = this.fb.group({
            'controlUsername': [null, [Validators.required, Validators.minLength(3)], [this.checkLogin.bind(this)]],
            'controlEmail': [null, [Validators.required, Validators.minLength(3), Validators.email], [this.checkEmail.bind(this)]],
            'controlPassword': [null, [Validators.required, Validators.minLength(3)]],
        });
    }

    ngOnInit() {
    }

    register() {
        this.accountService.createAccount(this.user).subscribe(() => {
                this.router.navigate(['/login']);
            }, err => {
                this.errorMessage = err.json().errorMessage;
            }
        )
    }

    checkLogin(control: FormControl): Observable<any> {
        return this.accountService.checkLogin(control.value).map(isExist => {
                return isExist ? {usernameExist: true} : null;
            }
        );
    }

    checkEmail(control: FormControl): Observable<any> {
        return this.accountService.checkEmail(control.value).map(isExist => {
            return isExist ? {emailExist: true} : null;
            }
        );
    }

}
