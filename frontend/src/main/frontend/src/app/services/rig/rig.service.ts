import {Injectable} from "@angular/core";
import {Headers,Http} from "@angular/http";
import {Rig} from "../../model/rig.model";
import {AppComponent} from "../../app.component";
import {AuthService} from "../auth.service";

@Injectable()
export class RigService {

  private rigsUrl = AppComponent.API_URL + '/api/rigs';

  private headers = new Headers({
    'Content-Type': 'application/json',
    'Authorization': 'Basic ' + this.authService.getToken()
  });

  constructor(private http: Http,
              private authService: AuthService) {
  }

  createRig(rig: Rig) {
    return this.http.post(this.rigsUrl, rig, {headers: this.headers});
  }

  getAllRigs() {
    return this.http.get('/api/rigs')
      .map(resp => resp.json());
  }

  getRigsById(id: number) {
    return this.http.get('/api/rigs/rig/' + id)
      .map(resp => resp.json());
  }

  removeRigById(id: number) {
    return this.http.delete('/api/rigs/rig/' + id);
  }

}
