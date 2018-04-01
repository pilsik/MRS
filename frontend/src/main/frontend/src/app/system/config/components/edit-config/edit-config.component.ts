import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';

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

  @Input()
  miners: Miner[];

  editingConfig: MinerConfig;
  errorMessage: string;

  constructor(private configService: ConfigService) {
  }

  @Output()
  onEditConfig = new EventEmitter<number>();

  @ViewChild('editModal')
  editModal: TemplateRef<any>;

  ngOnInit() {
  }

  open(config: MinerConfig) {
    this.errorMessage = null;
    this.editingConfig = <MinerConfig>UtilService.deepCopy(config);
  }

  editConfig(config: MinerConfig) {
    this.configService.editConfig(config).subscribe(
      data => {
        this.onEditConfig.emit(data.status);
        this.errorMessage = null;
      },
      err => this.errorMessage = err.json().errorMessage
    );
  }

  setDefaultConfigCommandLine(index: number) {
    this.editingConfig.commandLine = this.miners[index].defaultCommandLineWithParameters;
  }
}
