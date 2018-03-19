import {Component, OnInit} from '@angular/core';
import {Rig} from "../../model/rig.model";
import {RigService} from "../../services/rig/rig.service";

@Component({
  selector: 'app-rigs',
  templateUrl: './rigs.component.html',
  styleUrls: ['./rigs.component.css']
})
export class RigsComponent implements OnInit {

  rigs: Rig[];
  statusCode: number;
  requestProcessing = false;

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

  private deleteRig(id: number) {
    this.preProcessConfigurations();
    this.rigService.removeRigById(id).subscribe(
      successCode => {
        this.statusCode = successCode;
        this.getAllRigs();
      },
      errorCode => this.statusCode = errorCode
    );
  }

  private preProcessConfigurations() {
    this.statusCode = null;
    this.requestProcessing = true;
  }

}
