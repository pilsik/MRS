import {NgModule} from "@angular/core";
import {Routes, RouterModule, PreloadAllModules} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {UrlPermission} from "./urlPermission/url.permission";


const appRoutes: Routes = [
  {path: 'profile', component: ProfileComponent, canActivate: [UrlPermission]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'rigs', loadChildren: './rig/rig.module#RigModule', canActivate: [UrlPermission]},
  {path: 'configs', loadChildren: './config/config.module#ConfigModule', canActivate: [UrlPermission]},
  {path: '**', redirectTo: '/login'}

];


@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes, {
      preloadingStrategy: PreloadAllModules
    })
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
