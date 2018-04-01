import {NgModule} from "@angular/core";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {AuthComponent} from "./components/auth/auth.component";
import {CommonModule} from "@angular/common";
import {AuthRoutingModule} from "./auth-routing.module";
import {AccountService} from "./services/account.service";
import {SharedModule} from "../shared/shared.module";

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    AuthComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    SharedModule
  ],
  providers: [
    AccountService,
  ]
})
export class AuthModule{}
