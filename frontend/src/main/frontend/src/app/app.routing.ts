import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {UrlPermission} from "./urlPermission/url.permission";
import {CreateRigComponent} from "./components/create-rig/create-rig.component";
import {RigsComponent} from "./components/rigs/rigs.component";


const appRoutes: Routes = [
  {path: 'profile', component: ProfileComponent, canActivate: [UrlPermission]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'rigs', component: RigsComponent, canActivate: [UrlPermission]},
  {path: '**', redirectTo: '/login'}
];

export const routing = RouterModule.forRoot(appRoutes);
