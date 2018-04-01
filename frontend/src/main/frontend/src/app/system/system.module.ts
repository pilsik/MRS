import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";

import {SharedModule} from "../shared/shared.module";
import {SystemRoutingModule} from "./system-routing.module";
import {SystemComponent} from "./system.component";
import {HeaderComponent} from "./shared/components/header/header.component";
import {RigModule} from "./rig/rig.module";
import {ConfigModule} from "./config/config.module";


@NgModule({
    declarations: [
        SystemComponent,
        HeaderComponent,
    ],
    imports: [
        CommonModule,
        SharedModule,
        SystemRoutingModule,
        RigModule,
        ConfigModule
    ]
})
export class SystemModule {
}
