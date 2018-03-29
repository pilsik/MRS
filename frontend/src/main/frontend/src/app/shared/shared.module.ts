import {NgModule} from "@angular/core";
import {AlertComponent} from "./components/alert/alert.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  declarations:[AlertComponent],
  exports:[AlertComponent],
  imports: [NgbModule]
})
export class SharedModule{}
