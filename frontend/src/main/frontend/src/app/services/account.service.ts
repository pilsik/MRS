import {Injectable} from "@angular/core";
import {User} from "../model/model.user";
import {Http} from "@angular/http";

@Injectable()
export class AccountService {
  constructor(public http: Http) { }

  createAccount(user:User){
    return this.http.post('/api/account/register',user)
      .map(resp=>resp.json());
  }
}
