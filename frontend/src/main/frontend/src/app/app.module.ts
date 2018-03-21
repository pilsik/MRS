import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {FormsModule} from '@angular/forms';
import {AuthService} from "./services/auth.service";
import {HttpModule} from "@angular/http";
import {AccountService} from "./services/account.service";
import {ProfileComponent} from './components/profile/profile.component';
import {routing} from "./app.routing";
import {FacebookModule} from "ngx-facebook";
import {UrlPermission} from "./urlPermission/url.permission";
import {CreateRigComponent} from './components/create-rig/create-rig.component';
import {RigService} from "./services/rig/rig.service";
import {RigsComponent} from './components/rigs/rigs.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from './components/alert/alert.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    CreateRigComponent,
    RigsComponent,
    AlertComponent,
  ],
  imports: [
    BrowserModule, HttpModule, FormsModule, routing, FacebookModule.forRoot(),NgbModule.forRoot(),
  ],
  providers: [AuthService, AccountService, UrlPermission, RigService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
