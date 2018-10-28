import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AboutService {
    constructor(private http: HttpClient) {
    }

    public getBuildInfo(): Observable<any> {
        return this.http.get('/actuator/info').pipe(
            map((response: any) => response),
            catchError(this.handleError));
    }

    private handleError() {
        const errMsg = 'AboutService: cannot get build info from http server.';
        console.error(errMsg); // log to console instead
        return throwError(errMsg);
    }
}
