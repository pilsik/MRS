import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {User} from "../model/model.user";
import "rxjs/add/operator/map";
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
    console.log('send get /api/account/login');
    return this.http.get(AppComponent.API_URL+"/api/account/login", options)
      .map((response: Response) => {
        // login successful if there's a jwt token in the response
        let user = response.text();// the returned user object is a principal object
        if (user) {
          // store user details  in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', user);
        }
      });
  }

  logOut() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    return this.http.post(AppComponent.API_URL+"/api/account/logout", {});
  }
}
