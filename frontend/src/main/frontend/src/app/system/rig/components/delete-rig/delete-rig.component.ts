import {Component, EventEmitter, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {RigService} from "../../services/rig.service";

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
  deleteModal;

  deletedRigId: number;

  constructor(private rigService: RigService) {
  }

  ngOnInit() {
  }

  open(id: number) {
    this.deletedRigId = id;
    this.deleteModal.show()
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
    this.deleteModal.hide();
  }

}
