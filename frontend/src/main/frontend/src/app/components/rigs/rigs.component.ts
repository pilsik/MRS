import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Rig} from "../../model/rig.model";
import {RigService} from "../../services/rig/rig.service";
import $ from 'jquery';
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-rigs',
  templateUrl: './rigs.component.html',
  styleUrls: ['./rigs.component.css']
})
export class RigsComponent implements OnInit, AfterViewInit {

  alertsStatusCode: number[] = [];
  rigs: Rig[];
  statusCode: number;
  deletedRigId: number;
  modalReference: NgbModalRef;
  editingRig: Rig = new Rig();

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
    this.editingRig = <Rig>this.deepCopy(rig);
    this.modalReference = this.modalService.open(editModal);
    this.modalReference.result.then(() => {
      this.editingRig = null;
      this.modalService.open(editModal).close()
    });
  }

  open(deleteModal, id) {
    this.deletedRigId = id;
    this.modalReference = this.modalService.open(deleteModal);
    this.modalReference.result.then(() => {
      this.deletedRigId = 0;
      this.modalService.open(deleteModal).close()
    });
  }

  deleteRig(id: number) {
    this.rigService.removeRigById(id).subscribe(
      successCode => {
        this.showAlert(successCode);
        this.getAllRigs();
      },
      errorCode => this.showAlert(errorCode)
    );
    this.modalReference.close();
    setTimeout(() => this.statusCode = null, 3000);
  }

  editRig(rig: Rig) {
    this.rigService.editRig(rig).subscribe(
      successCode => {
        this.showAlert(successCode);
        this.getAllRigs();
      },
      errorCode => this.showAlert(errorCode)
    );
    this.modalReference.close();
    setTimeout(() => this.statusCode = null, 3000);
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
