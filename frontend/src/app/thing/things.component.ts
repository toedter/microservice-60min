import {Component, OnInit} from '@angular/core';
import {Thing} from './thing';
import {ThingsService} from './things.service';


@Component({
    selector: 'things',
    templateUrl: 'things.component.html',
    providers: [ThingsService],
})
export class ThingsComponent implements OnInit{
    things: Thing[];
    pageInfo: any;
    pageArray: any;
    links: any;

    constructor(private thingsService: ThingsService) {
    }

    ngOnInit() {
      this.reload(undefined, 0);
    }

    reload(uri?:string, page?: number) {
      this.thingsService.getThings(uri, page).subscribe(
        (response: any) => {
          this.things = response._embedded['things']
          this.links = response._links;
          this.pageInfo = response.page;
          this.pageArray = new Array<number>(response.page.totalPages);
        },
        error => console.error('ThingsComponent: cannot get things from ThingService')
      );
    }
}
