import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {AuthComponent} from "./components/auth/auth.component";

const authRoutes: Routes = [
  {path: '', component: AuthComponent, children: [
    {path: 'login', component: LoginComponent},
    {path: 'registration', component: RegisterComponent},
  ]
  }];

@NgModule({
  imports: [
    RouterModule.forChild(authRoutes)
  ],
  exports: [RouterModule],
})
export class AuthRoutingModule {
}
