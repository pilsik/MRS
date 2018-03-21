import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {Rig} from "../../model/rig.model";
import {AppComponent} from "../../app.component";
import {AuthService} from "../auth.service";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/catch';

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

  getAllRigs(): Observable<Rig[]> {
    return this.http.get(this.rigsUrl, {headers: this.headers})
      .map(resp => resp.json());
  }

  getRigsById(id: number) {
    return this.http.get(this.rigsUrl + '/rig/' + id, {headers: this.headers})
      .map(resp => resp.json());
  }

  removeRigById(id: number) {
    return this.http.delete(this.rigsUrl + '/rig/' + id, {headers: this.headers})
      .map(success => success.status)
      .catch(this.handleError);
  }

  editRig(rig: Rig) {
    return this.http.put(this.rigsUrl + '/rig/' + rig.id.toString(), rig, {headers: this.headers});
  }

  private handleError(error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }
}
