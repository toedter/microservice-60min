import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Observable} from 'rxjs/Observable';
import {Thing} from './thing';

@Injectable()
export class ThingsService {
    constructor(private http: Http) {
    }

    public getThings(): Observable<Thing[]> {
        let uri: string = '/api/things';

        if (!document.location.hostname || document.location.hostname === 'localhost') {
            uri = 'http://localhost:8080' + uri;
        }

        let observable: Observable<Thing[]> =
            this.http.get(uri)
                .map((response: Response) => response.json()._embedded['things'])
                .catch(this.handleError);

        return observable;
    }

    private handleError(error: any) {
        let errMsg = 'ThingsService: cannot get things from http server.';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}
