import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {SystemComponent} from "./system.component";
import {UrlPermission} from "../shared/urlPermission/url.permission";
import {RigsComponent} from "app/system/rig/components/rigs/rigs.component";
import {ConfigsComponent} from "app/system/config/components/configs/configs.component";

const systemRoutes: Routes = [
    {path: 'system', component: SystemComponent, canActivate: [UrlPermission], children: [
        {path: 'rigs', component: RigsComponent},
        {path: 'configs', component: ConfigsComponent}
    ]
    }];

@NgModule({
    imports: [
        RouterModule.forChild(systemRoutes)
    ],
    exports: [RouterModule],
})
export class SystemRoutingModule {
}
