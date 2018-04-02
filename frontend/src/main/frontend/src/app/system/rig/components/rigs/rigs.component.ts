import {Component, OnInit} from '@angular/core';

import {RigService} from "../../services/rig.service";
import {Rig} from "../../../../shared/models/rig.model";
import {MinerConfig} from "../../../../shared/models/minerConfig.model";

@Component({
    selector: 'app-rigs',
    templateUrl: './rigs.component.html',
    styleUrls: ['./rigs.component.css'],
})
export class RigsComponent implements OnInit {

    alertsStatusCode: number[] = [];
    rigs: Rig[];
    statusCode: number;
    configs: MinerConfig[];

    errorMessage: string;

    constructor(private rigService: RigService) {
    }

    ngOnInit() {
        this.rigService.updateHeaders();
        this.getAllRigs();
        this.getAllConfigs();
    }

    private getAllRigs() {
        this.rigService.getAllRigs().subscribe(
            data => this.rigs = data,
            errorCode => this.statusCode = errorCode);
    }

    private getAllConfigs() {
        this.rigService.getAllConfigs().subscribe(
            data => this.configs = data,
            errorCode => this.statusCode = errorCode);
    }

    updateRigList(statusCode) {
        this.getAllRigs();
        this.showAlert(statusCode);
    }

    showAlert(statusCode: number) {
        this.alertsStatusCode.push(statusCode);
        setTimeout(() => this.alertsStatusCode.splice(0, 1), 3000);
    }


}
