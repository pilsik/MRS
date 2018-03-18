import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {User} from "../model/user.model";
import "rxjs/add/operator/map";
import {Observable} from "rxjs/Observable";
import {AppComponent} from "../app.component";

@Injectable()
export class AuthService {

  constructor(public http: Http) {
  }

  public logIn(user: User) {

    let headers = new Headers();
    headers.append('Accept', 'application/json')
    // creating base64 encoded String from user name and password
    var base64Credential: string = btoa(user.username + ':' + user.password);
    headers.append("Authorization", "Basic " + base64Credential);
    let options = new RequestOptions();
    options.headers = headers;

    return this.http.get(AppComponent.API_URL+'/api/account/login', options)
      .map((response: Response) => {
        // login successful if there's a jwt token in the response
        let user = response.text();// the returned user object is a principal objectpublic
        if (user) {
          // store user details  in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', user);
          localStorage.setItem('authorization', base64Credential);
        }
      });
    ;
  }

  getToken(): String {
    var token = localStorage.getItem('authorization');
    return token ? token : "";
  }

  logOut() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    localStorage.removeItem('authorization');
    return this.http.post("/api/account/logout", {});
  }
}
