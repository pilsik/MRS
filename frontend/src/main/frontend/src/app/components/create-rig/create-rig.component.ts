import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Rig} from "../../model/rig.model";
import {RigService} from "../../services/rig/rig.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-rig',
  templateUrl: './create-rig.component.html',
  styleUrls: ['./create-rig.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreateRigComponent implements OnInit {

  rig: Rig = new Rig();
  errorMessage: string;

  constructor(public rigService: RigService, public router: Router) { }

  ngOnInit() {
  }

  createRig(){
    this.rigService.createRig(this.rig).subscribe(data => {
        this.router.navigate(['/profile']);
      }, err => {
        this.errorMessage = err.json().errorMessage;
      }
    )
  }

}
