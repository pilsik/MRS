import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AlertComponent implements OnInit {

  @Input('alert') alertStatus: number;
  @Input('typeEntity') typeEntity: string;

  typeMessage: string;
  message: string;

  constructor() {
  }

  ngOnInit() {
    switch (this.alertStatus) {
      case 201: {
        this.typeMessage = 'success';
        this.message = this.typeEntity + ' added successfully.';
        break;
      }
      case 409: {
        this.typeMessage = 'danger';
        this.message = this.typeEntity + ' already exists.';
        break;
      }
      case 200: {
        this.typeMessage = 'success';
        this.message = this.typeEntity + ' update successfully.';
        break;
      }
      case 204: {
        this.typeMessage = 'success';
        this.message = this.typeEntity + ' deleted successfully.';
        break;
      }
      case 500: {
        this.typeMessage = 'danger';
        this.message = 'error';
        break;
      }
      default: {
        this.typeMessage = 'success';
        this.message = this.alertStatus.toString();
        break;
      }
    }
  }

}
