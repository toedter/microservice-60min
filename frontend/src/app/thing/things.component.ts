import {Component, OnInit} from '@angular/core';
import {Thing} from './thing';
import {ThingsService} from './things.service';


@Component({
    selector: 'app-things',
    templateUrl: 'things.component.html'
})
export class ThingsComponent implements OnInit {
    things: Thing[];
    pageInfo: any;
    pageArray: any;
    links: any;

    constructor(private thingsService: ThingsService) {
    }

    ngOnInit() {
        this.reload(undefined, 0);
    }

    reload(uri?: string, page?: number) {
        this.thingsService.getThings(uri, page).subscribe(
            (response: any) => {
                this.things = response._embedded['ms60min:things'];
                this.links = response._links;
                this.pageInfo = response.page;
                this.pageArray = new Array<number>(response.page.totalPages);
            },
            () => console.error('ThingsComponent: cannot get things from ThingService')
        );
    }
}
