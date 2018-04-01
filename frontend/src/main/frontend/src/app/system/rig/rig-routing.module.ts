import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {RigsComponent} from "./components/rigs/rigs.component";

const rigRoutes: Routes = [
  {
    path: '', component: RigsComponent,
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(rigRoutes)
  ],
  exports: [RouterModule]
})
export class RigRoutingModule {
}
