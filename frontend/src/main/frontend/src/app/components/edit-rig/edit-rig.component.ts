import {Component, EventEmitter, OnInit, Output, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {Rig} from "../../model/rig.model";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {RigService} from "../../services/rig/rig.service";
import {UtilService} from "../../services/util.service";

@Component({
  selector: 'app-edit-rig',
  templateUrl: './edit-rig.component.html',
  styleUrls: ['./edit-rig.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EditRigComponent implements OnInit {

  editingRig: Rig;
  errorMessage: string;

  constructor(private rigService: RigService, private modalService: NgbModal) {
  }

  modalReference: NgbModalRef;

  @Output()
  onEditRig = new EventEmitter<number>();

  @ViewChild('editModal')
  editModal: TemplateRef<any>;

  ngOnInit() {
  }

  open(rig: Rig) {
    this.errorMessage = null;
    this.editingRig = <Rig>UtilService.deepCopy(rig);
    this.modalReference = this.modalService.open(this.editModal);
    this.modalReference.result.then(() => {
      this.editingRig = null;
    });
  }

  editRig(rig: Rig) {
    this.rigService.editRig(rig).subscribe(
      data => {
        this.onEditRig.emit(data.status);
        this.modalReference.close();
        this.errorMessage = null;
      },
      err => this.errorMessage = err.json().errorMessage
    );
  }

}
