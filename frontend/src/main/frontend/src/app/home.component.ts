import { Component, OnInit } from '@angular/core';
import { AppService } from './app.service';
import { HttpClient } from '@angular/common/http';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent {

  title = 'Demo';
  users = {};

  constructor(private app: AppService, private http: HttpClient) {
    http.get('/api/users').subscribe(data => this.users = data);
  }

  authenticated() { return this.app.authenticated; }

}
