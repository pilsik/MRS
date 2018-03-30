import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {ConfigsComponent} from "./components/configs/configs.component";


const configRoutes: Routes = [
  {
    path: '', component: ConfigsComponent,
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(configRoutes)
  ],
  exports: [RouterModule]
})
export class ConfigRoutingModule { }
