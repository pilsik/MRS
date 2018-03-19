import {Injectable} from "@angular/core";
import {User} from "../model/user.model";
import {Http} from "@angular/http";
import {AppComponent} from "../app.component";

@Injectable()
export class AccountService {

  private createAccountUrl = AppComponent.API_URL + '/api/account/register';

  constructor(public http: Http) {
  }

  createAccount(user: User) {
    return this.http.post(this.createAccountUrl, user)
      .map(resp => resp.json());
  }
}
