import {Injectable} from '@angular/core';

import {Headers, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {AppComponent} from "../../../app.component";
import {AuthService} from "../../../shared/services/auth.service";
import {MinerConfig} from "../../../shared/models/minerConfig.model";


@Injectable()
export class ConfigService {

  private configsUrl = AppComponent.API_URL + '/api/configs';

  private headers = new Headers({
    'Content-Type': 'application/json',
    'Authorization': 'Basic ' + this.authService.getToken()
  });

  constructor(private http: Http,
              private authService: AuthService) {
  }

  createConfig(config: MinerConfig) {
    return this.http.post(this.configsUrl, config, {headers: this.headers});
  }

  getAllConfigs(): Observable<MinerConfig[]> {
    return this.http.get(this.configsUrl, {headers: this.headers})
      .map(resp => resp.json());
  }

  getConfigById(id: number) {
    return this.http.get(this.configsUrl + '/config/' + id, {headers: this.headers})
      .map(resp => resp.json());
  }

  removeConfigById(id: number) {
    return this.http.delete(this.configsUrl + '/config/' + id, {headers: this.headers})
      .map(success => success.status)
      .catch(this.handleError);
  }

  editConfig(config: MinerConfig) {
    return this.http.put(this.configsUrl + '/config/' + config.id.toString(), config, {headers: this.headers});
  }

  private handleError(error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }

}
