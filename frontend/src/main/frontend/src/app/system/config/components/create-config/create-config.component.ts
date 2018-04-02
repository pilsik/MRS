import {Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import {FormGroup, Validators, FormBuilder} from '@angular/forms';

import {ConfigService} from "../../service/config.service";
import {Miner} from "../../../../shared/models/miner.model";
import {MinerConfig} from "../../../../shared/models/minerConfig.model";


@Component({
    selector: 'app-create-config',
    templateUrl: './create-config.component.html',
    styleUrls: ['./create-config.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class CreateConfigComponent implements OnInit {

    validatingForm: FormGroup;

    @Input()
    miners: Miner[];

    config: MinerConfig = new MinerConfig();
    errorMessage: string;

    @Output()
    onCreateConfig = new EventEmitter<number>();

    @ViewChild('createModal')
    createModal;

    constructor(public configService: ConfigService, private fb: FormBuilder) {
        this.validatingForm = this.fb.group({
            'requiredConfigName': [null, Validators.required],
            'requiredConfigMiner': [null, Validators.required],
            'requiredConfigCommandLine': [null, Validators.required],
        });
    }

    ngOnInit() {
    }

    open() {
        this.validatingForm.reset();
        this.config = new MinerConfig();
        this.createModal.show();
    }

    createConfig() {
        this.configService.createConfig(this.config).subscribe(
            data => {
                this.onCreateConfig.emit(data.status);
                this.errorMessage = null;
                this.createModal.hide();
            },
            err => this.errorMessage = err.json().errorMessage
        );
    }

    setDefaultConfigCommandLine(index: number) {
        this.config.commandLine = this.miners[index].defaultCommandLineWithParameters;
    }

}
