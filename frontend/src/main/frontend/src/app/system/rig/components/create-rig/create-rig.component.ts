import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {FormGroup, Validators, FormBuilder} from '@angular/forms';

import {Rig} from "../../../../shared/models/rig.model";
import {Router} from "@angular/router";
import {RigService} from "../../services/rig.service";
import {MinerConfig} from "../../../../shared/models/minerConfig.model";

@Component({
    selector: 'app-create-rig',
    templateUrl: './create-rig.component.html',
    styleUrls: ['./create-rig.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class CreateRigComponent implements OnInit {

    @Input()
    configs: MinerConfig[];

    validatingForm: FormGroup;

    rig: Rig = new Rig();
    errorMessage: string;

    @Output()
    onCreateRig = new EventEmitter<number>();

    @ViewChild('createModal')
    createModal;

    constructor(public rigService: RigService, public router: Router, private fb: FormBuilder) {
        this.validatingForm = this.fb.group({
            'controllerRigName': [null, [Validators.required, Validators.minLength(3)]],
            'controllerRigPassword': [null, [Validators.required, Validators.minLength(3)]],
            'controllerRigConfig': [null, null],
        });
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
        this.validatingForm.reset();
        this.rig = new Rig();
        this.createModal.show();
    }


}
