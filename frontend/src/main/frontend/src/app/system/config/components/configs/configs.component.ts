import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import {ConfigService} from "../../service/config.service";
import {MinerConfig} from "../../../../shared/models/minerConfig.model";
import {Miner} from "../../../../shared/models/miner.model";
import {MinerService} from "../../../../shared/services/miner.service";


@Component({
  selector: 'app-configs',
  templateUrl: './configs.component.html',
  styleUrls: ['./configs.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ConfigsComponent implements OnInit {

  alertsStatusCode: number[] = [];
  statusCode: number;
  configs: MinerConfig[];
  miners: Miner[];

  constructor(private configService: ConfigService, private minerService: MinerService) { }

  ngOnInit() {
    this.getAllConfigs();
    this.getAllMiners();
  }

  private getAllConfigs() {
    this.configService.getAllConfigs().subscribe(
      data => this.configs = data,
      errorCode => this.statusCode = errorCode);
  }

  showAlert(statusCode: number) {
    this.alertsStatusCode.push(statusCode);
    setTimeout(() => this.alertsStatusCode.splice(0, 1), 2000);
  }

  private getAllMiners() {
    this.minerService.getAllMiners().subscribe(
      data => this.miners = data,
      errorCode => this.statusCode = errorCode
    )
  }

  updateConfigList(statusCode) {
    this.getAllConfigs();
    this.showAlert(statusCode);
  }

}
