import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";

import {ConfigRoutingModule} from "./config-routing.module";
import {ConfigService} from "./service/config.service";
import {ConfigsComponent} from "./components/configs/configs.component";
import {CreateConfigComponent} from "./components/create-config/create-config.component";
import {DeleteConfigComponent} from "./components/delete-config/delete-config.component";
import {EditConfigComponent} from "./components/edit-config/edit-config.component";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    ConfigRoutingModule
  ],
  declarations: [ConfigsComponent, CreateConfigComponent, DeleteConfigComponent, EditConfigComponent],
  providers: [ConfigService]
})
export class ConfigModule {}
