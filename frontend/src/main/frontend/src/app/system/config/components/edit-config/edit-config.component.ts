import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {FormGroup, Validators, FormBuilder} from '@angular/forms';

import {ConfigService} from "../../service/config.service";
import {Miner} from "../../../../shared/models/miner.model";
import {MinerConfig} from "../../../../shared/models/minerConfig.model";
import {UtilService} from "../../../../shared/services/util.service";


@Component({
    selector: 'app-edit-config',
    templateUrl: './edit-config.component.html',
    styleUrls: ['./edit-config.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class EditConfigComponent implements OnInit {

    validatingForm: FormGroup;

    @Input()
    miners: Miner[];

    editingConfig: MinerConfig = new MinerConfig();
    errorMessage: string;

    constructor(private configService: ConfigService, private fb: FormBuilder) {
        this.validatingForm = this.fb.group({
            'requiredConfigName': [null, Validators.required],
            'requiredConfigMiner': [null, Validators.required],
            'requiredConfigCommandLine': [null, Validators.required],
        });
    }

    @Output()
    onEditConfig = new EventEmitter<number>();

    @ViewChild('editModal')
    editModal;

    ngOnInit() {
    }

    open(config: MinerConfig) {
        this.errorMessage = null;
        this.editModal.show();
        this.editingConfig = <MinerConfig>UtilService.deepCopy(config);
    }

    editConfig(config: MinerConfig) {
        this.configService.editConfig(config).subscribe(
            data => {
                this.onEditConfig.emit(data.status);
                this.errorMessage = null;
                this.editModal.hide();
            },
            err => this.errorMessage = err.json().errorMessage
        );
    }

    setDefaultConfigCommandLine(index: number) {
        this.editingConfig.commandLine = this.miners[index].defaultCommandLineWithParameters;
    }
}
