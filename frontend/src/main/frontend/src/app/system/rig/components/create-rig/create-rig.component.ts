import {Component, EventEmitter, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {Rig} from "../../../../shared/models/rig.model";
import {Router} from "@angular/router";
import {RigService} from "../../services/rig.service";


@Component({
  selector: 'app-create-rig',
  templateUrl: './create-rig.component.html',
  styleUrls: ['./create-rig.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreateRigComponent implements OnInit {

  rig: Rig = new Rig();
  errorMessage: string;

  @Output()
  onCreateRig = new EventEmitter<number>();

  @ViewChild('createModal')
  createModal;

  constructor(public rigService: RigService, public router: Router) {
  }

  ngOnInit() {
  }

  createRig() {
    this.rigService.createRig(this.rig).subscribe(
      data => {
        this.onCreateRig.emit(data.status);
        this.createModal.hide();
        this.errorMessage = null;
      },
      err => this.errorMessage = err.json().errorMessage
    );
  }

  open() {
    this.rig = new Rig();
    this.createModal.show();
  }


}
