import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class AppService {

  authenticated = false;

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback) {

    const headers = new HttpHeaders(credentials ? {
      authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password),
      'X-Requested-With': 'XMLHttpRequest'
    } : {});

    this.http.get('/home', {headers: headers}).catch(e => this.handleError(e));

  }

}
