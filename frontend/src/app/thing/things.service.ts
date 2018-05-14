import {Thing} from './thing';
import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from 'rxjs';
import {catchError, map} from "rxjs/operators";

@Injectable()
export class ThingsService {
    constructor(private http: HttpClient) {
    }

    public getThings(): Observable<Thing[]> {
        let uri: string = '/api/things';

        if (!document.location.hostname || document.location.hostname === 'localhost') {
            uri = 'http://localhost:8080' + uri;
        }

        let observable: Observable<Thing[]> =
            this.http.get(uri).pipe(
                map((response: any) => response._embedded['things']),
                catchError(this.handleError));

        return observable;
    }

    private handleError(error: any) {
        let errMsg = 'ThingsService: cannot get things from http server.';
        console.error(errMsg); // log to console instead
        return throwError(errMsg);
    }
}
