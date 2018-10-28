import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from 'rxjs';
import {catchError, map} from "rxjs/operators";

@Injectable()
export class AboutService {
    constructor(private http: HttpClient) {
    }

    public getBuildInfo(): Observable<any> {
        let uri = '/actuator/info';
        let observable: Observable<any> =
            this.http.get(uri).pipe(
                map((response: any) => response),
                catchError(this.handleError));

        return observable;
    }

    private handleError(error: any) {
        let errMsg = 'AboutService: cannot get build info from http server.';
        console.error(errMsg); // log to console instead
        return throwError(errMsg);
    }
}
