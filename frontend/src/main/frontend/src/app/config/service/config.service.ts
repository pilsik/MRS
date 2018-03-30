import {Injectable} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {AppComponent} from "../../app.component";
import {Headers, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Config} from "../../model/config.model";

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

  createConfig(config: Config) {
    return this.http.post(this.configsUrl, config, {headers: this.headers});
  }

  getAllConfigs(): Observable<Config[]> {
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

  editConfig(config: Config) {
    return this.http.put(this.configsUrl + '/config/' + config.id.toString(), config, {headers: this.headers});
  }

  private handleError(error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }

}
