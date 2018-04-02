import {Component, EventEmitter, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {FormGroup, Validators, FormBuilder} from '@angular/forms';

import {Rig} from "../../../../shared/models/rig.model";
import {RigService} from "../../services/rig.service";
import {UtilService} from "../../../../shared/services/util.service";

@Component({
  selector: 'app-edit-rig',
  templateUrl: './edit-rig.component.html',
  styleUrls: ['./edit-rig.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EditRigComponent implements OnInit {

  validatingForm: FormGroup;

  editingRig: Rig = new Rig();
  errorMessage: string;

  constructor(private rigService: RigService, private fb: FormBuilder) {
    this.validatingForm = this.fb.group({
      'controllerRigName': [null, [Validators.required, Validators.minLength(3)]],
      'controllerRigPassword': [null, [Validators.required, Validators.minLength(3)]],
    });
  }

  @Output()
  onEditRig = new EventEmitter<number>();

  @ViewChild('editModal')
  editModal;

  ngOnInit() {
  }

  open(rig: Rig) {
    this.errorMessage = null;
    this.editModal.show();
    this.editingRig = <Rig>UtilService.deepCopy(rig);
  }

  editRig(rig: Rig) {
    this.rigService.editRig(rig).subscribe(
      data => {
        this.onEditRig.emit(data.status);
        this.errorMessage = null;
        this.editModal.hide();
      },
      err => this.errorMessage = err.json().errorMessage
    );
  }

}
