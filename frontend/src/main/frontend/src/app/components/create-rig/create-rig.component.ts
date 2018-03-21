import {Component, EventEmitter, OnInit, Output, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {Rig} from "../../model/rig.model";
import {RigService} from "../../services/rig/rig.service";
import {Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-create-rig',
  templateUrl: './create-rig.component.html',
  styleUrls: ['./create-rig.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreateRigComponent implements OnInit {

  rig: Rig = new Rig();
  errorMessage: string;
  modalReference: NgbModalRef;

  @Output()
  onCreateRig = new EventEmitter<number>();

  @ViewChild('createModal')
  createModal: TemplateRef<any>;

  responseCode: number;

  constructor(public rigService: RigService, public router: Router, private modalService: NgbModal) {
  }

  ngOnInit() {
  }

  createRig() {
    this.rigService.createRig(this.rig).subscribe(
      data => {this.onCreateRig.emit(data.status);this.modalReference.close(); this.errorMessage = null;},
      err => this.errorMessage = err.json().errorMessage
    );
  }

  open() {
    this.modalReference = this.modalService.open(this.createModal);
    this.modalReference.result.then(() => {
      this.rig = new Rig();
    });
  }


}
