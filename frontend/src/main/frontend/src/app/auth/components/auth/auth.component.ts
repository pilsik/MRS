import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AuthComponent implements OnInit {

  constructor(private  router: Router) { }

  ngOnInit() {
    this.router.navigate(['/login']);
  }

}
