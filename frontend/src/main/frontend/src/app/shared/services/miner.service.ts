import {Injectable} from "@angular/core";
import {AppComponent} from "../../app.component";
import {AuthService} from "./auth.service";
import {Observable} from "rxjs/Observable";
import {Miner} from "../models/miner.model";
import {Headers, Http} from "@angular/http";

@Injectable()
export class MinerService{

  private minersUrl = AppComponent.API_URL + '/api/miners';

  constructor(private http: Http,
              private authService: AuthService ) {
  }

  private headers = new Headers({
    'Content-Type': 'application/json',
    'Authorization': 'Basic ' + this.authService.getToken()
  });

  getAllMiners(): Observable<Miner[]> {
    return this.http.get(this.minersUrl, {headers: this.headers})
      .map(resp => resp.json());
  }

  getMinerById(id: number) {
    return this.http.get(this.minersUrl + '/miner/' + id, {headers: this.headers})
      .map(resp => resp.json());
  }

}
