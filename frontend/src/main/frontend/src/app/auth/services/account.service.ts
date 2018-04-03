import {Injectable} from "@angular/core";
import {User} from "../../shared/models/user.model";
import {Http} from "@angular/http";
import {AppComponent} from "../../app.component";

@Injectable()
export class AccountService {

    private createAccountUrl = AppComponent.API_URL + '/api/account/register';

    constructor(public http: Http) {
    }

    createAccount(user: User) {
        return this.http.post(this.createAccountUrl, user)
            .map(resp => resp.json());
    }

    checkLogin(login: string) {
        return this.http.get(this.createAccountUrl + '/checkLogin', {
            params: {
                login: login,
            }
        }).map(resp => resp.json());
    }

    checkEmail(email: string) {
        return this.http.get(this.createAccountUrl + '/checkEmail', {
            params: {
                email: email,
            }
        })
            .map(resp => resp.json());
    }
}
