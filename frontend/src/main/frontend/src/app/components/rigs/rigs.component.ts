import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Rig} from "../../model/rig.model";
import {RigService} from "../../services/rig/rig.service";
import $ from 'jquery';

@Component({
  selector: 'app-rigs',
  templateUrl: './rigs.component.html',
  styleUrls: ['./rigs.component.css']
})
export class RigsComponent implements OnInit, AfterViewInit {

  rigs: Rig[];
  statusCode: number;
  requestProcessing = false;

  constructor(private rigService: RigService) {
  }

  ngOnInit() {
    this.getAllRigs();
  }

  ngAfterViewInit(){
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
    $(document).ready (function(){
      $("#error-alert").hide();
      $("#errorAlert").click(function showAlert() {
        $("#error-alert").fadeTo(2000, 500).slideUp(1000, function(){
          $("#error-alert").slideUp(1000);
        });
      });
    });
    $(document).ready (function(){
      $("#success-alert").hide();
      $("#successAlert").click(function showAlert() {
        $("#success-alert").fadeTo(2000, 500).slideUp(500, function(){
          $("#success-alert").slideUp(500);
        });
      });
    });
  }

  private getAllRigs() {
    this.rigService.getAllRigs().subscribe(
      data => this.rigs = data,
      errorCode => this.statusCode = errorCode);
  }

  private deleteRig(id: number) {
    this.preProcessConfigurations();
    this.rigService.removeRigById(id).subscribe(
      successCode => {
        this.statusCode = successCode;
        this.getAllRigs();
      },
      errorCode => this.statusCode = errorCode
    );
  }

  private preProcessConfigurations() {
    this.statusCode = null;
    this.requestProcessing = true;
  }

}
