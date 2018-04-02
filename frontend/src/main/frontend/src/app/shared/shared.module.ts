import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UrlPermission} from "./urlPermission/url.permission";
import {AuthService} from "./services/auth.service";
import {MDBBootstrapModule} from "../typescripts/free/index";
import {AgmCoreModule} from "@agm/core";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MinerService} from "./services/miner.service";
import {AlertComponent} from "./alert/alert.component";

@NgModule({
    declarations: [AlertComponent],
    exports: [AlertComponent, FormsModule, MDBBootstrapModule, AgmCoreModule, ReactiveFormsModule],
    imports: [
        FormsModule,
        MDBBootstrapModule.forRoot(),
        AgmCoreModule.forRoot({
            // https://developers.google.com/maps/documentation/javascript/get-api-key?hl=en#key
            apiKey: 'Your_api_key'
        }),
        BrowserAnimationsModule,
        ReactiveFormsModule,
    ],
    providers: [UrlPermission, AuthService, MinerService]
})
export class SharedModule {
}
