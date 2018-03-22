import {Component, OnInit, ViewChild} from '@angular/core';
import {Rig} from "../../model/rig.model";
import {RigService} from "../../services/rig/rig.service";
import {DeleteRigComponent} from "../delete-rig/delete-rig.component";
import {CreateRigComponent} from "../create-rig/create-rig.component";
import {EditRigComponent} from "../edit-rig/edit-rig.component";

@Component({
  selector: 'app-rigs',
  templateUrl: './rigs.component.html',
  styleUrls: ['./rigs.component.css'],
})
export class RigsComponent implements OnInit {

/*  @ViewChild('deleteModal')
  deleteModalComponent: DeleteRigComponent;*/

  alertsStatusCode: number[] = [];
  rigs: Rig[];
  statusCode: number;

  errorMessage: string;

  constructor(private rigService: RigService) {
  }

  ngOnInit() {
    this.getAllRigs();
  }

  private getAllRigs() {
    this.rigService.getAllRigs().subscribe(
      data => this.rigs = data,
      errorCode => this.statusCode = errorCode);
  }

  updateRigList(statusCode) {
    this.getAllRigs();
    this.showAlert(statusCode);
  }

  showAlert(statusCode: number) {
    this.alertsStatusCode.push(statusCode);
    setTimeout(() => this.alertsStatusCode.splice(0, 1), 2000);
  }

}
