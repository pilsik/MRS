import {Component, EventEmitter, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
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
  deleteModal;

  deletedConfigId: number;

  constructor(private configService: ConfigService) {
  }

  ngOnInit() {
  }

  open(id: number) {
    this.deletedConfigId = id;
    this.deleteModal.show()
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
    this.deleteModal.hide();
  }

}
