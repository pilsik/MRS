import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {trigger, transition, style, animate} from '@angular/animations'

@Component({
    selector: 'app-alert',
    templateUrl: './alert.component.html',
    styleUrls: ['./alert.component.css'],
    encapsulation: ViewEncapsulation.None,
    animations: [
        trigger(
            'smoothAppearance',
            [
                transition(
                    ':enter', [
                        style({transform: 'translateX(100%)', opacity: 0}),
                        animate('500ms', style({transform: 'translateX(0)', 'opacity': 1}))
                    ]
                ),
                transition(
                    ':leave', [
                        style({transform: 'translateX(0)', 'opacity': 1}),
                        animate('500ms', style({transform: 'translateX(100%)', 'opacity': 0}))
                    ])]
        )
    ]
})
export class AlertComponent implements OnInit {

    @Input('alert')
    alertStatus: number;

    @Input('typeEntity')
    typeEntity: string;

    typeMessage: string;
    message: string;

    isError: boolean = false;
    isSuccess: boolean = false;
    isWarning: boolean = false;
    isInfo: boolean = false;

    show: boolean = true;

    constructor() {
        setTimeout(() => this.show = false, 2000);
    }

    ngOnInit() {
        switch (this.alertStatus) {
            case 201: {
                this.typeMessage = '#00c851';
                this.message = this.typeEntity + ' added successfully.';
                this.isSuccess = true;
                break;
            }
            case 409: {
                this.typeMessage = '#ff8800';
                this.message = this.typeEntity + ' already exists.';
                this.isWarning = true;
                break;
            }
            case 200: {
                this.typeMessage = '#00c851';
                this.message = this.typeEntity + ' update successfully.';
                this.isSuccess = true;
                break;
            }
            case 204: {
                this.typeMessage = '#00c851';
                this.message = this.typeEntity + ' deleted successfully.';
                this.isSuccess = true;
                break;
            }
            case 500: {
                this.typeMessage = '#ff3547';
                this.message = 'error';
                this.isError = true;
                break;
            }
            default: {
                this.typeMessage = '#33b5e5';
                this.message = this.alertStatus.toString();
                this.isInfo = true;
                break;
            }
        }
    }

}
