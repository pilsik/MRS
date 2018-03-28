import {Component, OnInit, ViewChild} from '@angular/core';
import {Rig} from "../../../model/rig.model";
import {RigService} from "../../services/rig.service";

@Component({
  selector: 'app-rigs',
  templateUrl: './rigs.component.html',
  styleUrls: ['./rigs.component.css'],
})
export class RigsComponent implements OnInit {

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
