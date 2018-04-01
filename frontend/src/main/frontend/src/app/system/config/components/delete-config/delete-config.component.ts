import {Component, EventEmitter, OnInit, Output, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {ConfigService} from "../../service/config.service";

@Component({
  selector: 'app-delete-config',
  templateUrl: './delete-config.component.html',
  styleUrls: ['./delete-config.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DeleteConfigComponent implements OnInit {

  @Output()
  onDeleteConfig = new EventEmitter<number>();

  @ViewChild('deleteModal')
  deleteModal: TemplateRef<any>;

  deletedConfigId: number;

  constructor(private configService: ConfigService) {
  }

  ngOnInit() {
  }

  open(id: number) {
    this.deletedConfigId = id;
   /* this.modalReference = this.modalService.open(this.deleteModal);
    this.modalReference.result.then(() => {
        this.modalService.open(this.deleteModal).close()
      },
      () => {
      });*/
  }

  deleteConfig(id: number) {
    this.configService.removeConfigById(id).subscribe(
      successCode => {
        this.onDeleteConfig.emit(successCode);
      }
      , errorCode => {
        console.log(errorCode);
        this.onDeleteConfig.emit(errorCode);
      });
    /*this.modalReference.close();*/
  }

}
