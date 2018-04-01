import {BrowserModule} from "@angular/platform-browser";
import {NgModule, NO_ERRORS_SCHEMA} from "@angular/core";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {AuthModule} from "./auth/auth.module";
import {AppRoutingModule} from "app/app-routing.module";
import {SystemModule} from "./system/system.module";

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        HttpModule,
        AppRoutingModule,
        AuthModule,
        SystemModule
    ],
    providers: [],
    bootstrap: [AppComponent],
    schemas: [NO_ERRORS_SCHEMA]
})
export class AppModule {
}
