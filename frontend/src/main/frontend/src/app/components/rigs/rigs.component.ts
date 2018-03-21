import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Rig} from "../../model/rig.model";
import {RigService} from "../../services/rig/rig.service";
import $ from 'jquery';
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {DeleteRigComponent} from "../delete-rig/delete-rig.component";
import {CreateRigComponent} from "../create-rig/create-rig.component";

@Component({
  selector: 'app-rigs',
  templateUrl: './rigs.component.html',
  styleUrls: ['./rigs.component.css'],
})
export class RigsComponent implements OnInit, AfterViewInit {

  @ViewChild('deleteModal')
  deleteModalComponent:DeleteRigComponent;

  @ViewChild('createModal')
  createModalComponent:CreateRigComponent;

  alertsStatusCode: number[] = [];
  rigs: Rig[];
  statusCode: number;
  modalReference: NgbModalRef;
  editingRig: Rig = new Rig();
  errorMessage: string;

  constructor(private rigService: RigService, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.getAllRigs();
  }

  ngAfterViewInit() {
    $(document).ready(function () {
      $("#mytable #checkall").click(function () {
        if ($("#mytable #checkall").is(':checked')) {
          $("#mytable input[type=checkbox]").each(function () {
            $(this).prop("checked", true);
          });

        } else {
          $("#mytable input[type=checkbox]").each(function () {
            $(this).prop("checked", false);
          });
        }
      });
      /* $("[data-toggle=tooltip]").tooltip();*/
    });
  }

  private getAllRigs() {
    this.rigService.getAllRigs().subscribe(
      data => this.rigs = data,
      errorCode => this.statusCode = errorCode);
  }

  openForEdit(editModal, rig) {
    this.errorMessage = null;
    this.editingRig = <Rig>this.deepCopy(rig);
    this.modalReference = this.modalService.open(editModal);
    this.modalReference.result.then(() => {
      this.editingRig = null;
      this.modalService.open(editModal).close()
    });
  }

  editRig(rig: Rig) {
    this.rigService.editRig(rig).subscribe(
      successCode => {
        this.showAlert(successCode.status);
        this.getAllRigs();
        this.modalReference.close();
        setTimeout(() => this.statusCode = null, 3000);
      },
      err => this.errorMessage = err.json().errorMessage
    );
  }

  deleteRig(id: number){
    this.deleteModalComponent.open(id);
  }

  createRig(){
    this.createModalComponent.open();
  }

  updateRigList(statusCode){
    this.getAllRigs();
    this.showAlert(statusCode);
  }

  showAlert(statusCode: number) {
    this.alertsStatusCode.push(statusCode);
    setTimeout(() => this.alertsStatusCode.splice(0, 1), 2000);
  }

  private deepCopy(obj) {
  var copy;

  // Handle the 3 simple types, and null or undefined
  if (null == obj || "object" != typeof obj) return obj;

  // Handle Date
  if (obj instanceof Date) {
    copy = new Date();
    copy.setTime(obj.getTime());
    return copy;
  }

  // Handle Array
  if (obj instanceof Array) {
    copy = [];
    for (var i = 0, len = obj.length; i < len; i++) {
      copy[i] = this.deepCopy(obj[i]);
    }
    return copy;
  }

  // Handle Object
  if (obj instanceof Object) {
    copy = {};
    for (var attr in obj) {
      if (obj.hasOwnProperty(attr)) copy[attr] = this.deepCopy(obj[attr]);
    }
    return copy;
  }

  throw new Error("Unable to copy obj! Its type isn't supported.");
}
}
