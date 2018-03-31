import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from "@angular/forms";

import {SharedModule} from "../shared/shared.module";
import {ConfigRoutingModule} from "./config-routing.module";
import {ConfigService} from "./service/config.service";
import {ConfigsComponent} from "./components/configs/configs.component";
import {CreateConfigComponent} from './components/create-config/create-config.component';
import {DeleteConfigComponent} from './components/delete-config/delete-config.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    ConfigRoutingModule,
    SharedModule
  ],
  declarations: [ConfigsComponent, CreateConfigComponent, DeleteConfigComponent],
  providers: [ConfigService]
})
export class ConfigModule {
}
