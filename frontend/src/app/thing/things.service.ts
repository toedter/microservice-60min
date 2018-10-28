import {Thing} from './thing';
import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from 'rxjs';
import {catchError, map} from "rxjs/operators";

@Injectable()
export class ThingsService {
    constructor(private http: HttpClient) {
    }

    public getThings(uri?: string, page?: number, size?: number): Observable<any> {
        if (!uri) {
            uri = '/api/things';
            if (!size) {
                size = 10;
            }
            if (page || size) {
                uri += '?'
            }
            if (page) {
                uri += 'page=' + page + '&';
            }

            uri += 'size=' + size;
        }

        let observable: Observable<Thing[]> =
            this.http.get(uri).pipe(
                map((response: any) => response),
                catchError(this.handleError));

        return observable;
    }

    private handleError(error: any) {
        let errMsg = 'ThingsService: cannot get things from http server.';
        console.error(errMsg); // log to console instead
        return throwError(errMsg);
    }
}
