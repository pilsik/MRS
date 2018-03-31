import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {MinerConfig} from "../../../model/minerConfig.model";
import {NgbModalRef, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ConfigService} from "../../service/config.service";
import {Miner} from "../../../model/miner.model";

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
  modalReference: NgbModalRef;

  @Output()
  onCreateConfig = new EventEmitter<number>();

  @ViewChild('createModal')
  createModal: TemplateRef<any>;

  responseCode: number;

  constructor(public configService: ConfigService, private modalService: NgbModal) {
  }

  ngOnInit() {
  }

  open() {
    this.modalReference = this.modalService.open(this.createModal);
    this.modalReference.result.then(() => {
      this.config = new MinerConfig();
    });
  }

  createConfig() {
    this.configService.createConfig(this.config).subscribe(
      data => {
        this.onCreateConfig.emit(data.status);
        this.modalReference.close();
        this.errorMessage = null;
      },
      err => this.errorMessage = err.json().errorMessage
    );
  }

  setDefaultConfigCommandLine(index: number){
    this.config.commandLine = this.miners[index].defaultCommandLineWithParameters;
  }

}
