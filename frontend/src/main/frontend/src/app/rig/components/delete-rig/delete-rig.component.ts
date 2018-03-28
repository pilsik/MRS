import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {RigService} from "../../services/rig/rig.service";

@Component({
  selector: 'app-delete-rig',
  templateUrl: './delete-rig.component.html',
  styleUrls: ['./delete-rig.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DeleteRigComponent implements OnInit {

  @Output()
  onDeleteRig = new EventEmitter<number>();

  @ViewChild('deleteModal')
  deleteModal: TemplateRef<any>;

  deletedRigId: number;

  modalReference: NgbModalRef;

  constructor(private rigService: RigService, private modalService: NgbModal) {
  }

  ngOnInit() {
  }

  open(id:number) {
    this.deletedRigId = id;
    this.modalReference = this.modalService.open(this.deleteModal);
    this.modalReference.result.then(() => {
      this.modalService.open(this.deleteModal).close()
    });
  }

  deleteRig(id: number) {
    this.rigService.removeRigById(id).subscribe(
      successCode => {
        this.onDeleteRig.emit(successCode);
      }
      , errorCode => {
        console.log(errorCode);
        this.onDeleteRig.emit(errorCode);
      });
    this.modalReference.close();
  }

}
