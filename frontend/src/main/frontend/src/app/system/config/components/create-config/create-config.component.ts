import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
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

  @Input()
  miners: Miner[];

  config: MinerConfig = new MinerConfig();
  errorMessage: string;

  @Output()
  onCreateConfig = new EventEmitter<number>();

  @ViewChild('createModal')
  createModal: TemplateRef<any>;

  responseCode: number;

  constructor(public configService: ConfigService) {
  }

  ngOnInit() {
  }

  open() {

  }

  createConfig() {
    this.configService.createConfig(this.config).subscribe(
      data => {
        this.onCreateConfig.emit(data.status);
        this.errorMessage = null;
      },
      err => this.errorMessage = err.json().errorMessage
    );
  }

  setDefaultConfigCommandLine(index: number) {
    this.config.commandLine = this.miners[index].defaultCommandLineWithParameters;
  }

}
