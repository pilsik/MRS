import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core";

import {CreateRigComponent} from "./components/create-rig/create-rig.component";
import {RigsComponent} from "./components/rigs/rigs.component";
import {DeleteRigComponent} from "./components/delete-rig/delete-rig.component";
import {EditRigComponent} from "./components/edit-rig/edit-rig.component";
import {RigService} from "./services/rig.service";
import {RigRoutingModule} from "./rig-routing.module";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    CreateRigComponent,
    RigsComponent,
    DeleteRigComponent,
    EditRigComponent,
  ],
  imports: [
    CommonModule,
    RigRoutingModule,
    SharedModule,
  ],
  providers: [RigService]
})
export class RigModule {}
