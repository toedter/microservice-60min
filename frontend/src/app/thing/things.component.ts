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

    constructor(private thingsService: ThingsService) {
    }

    ngOnInit() {
        this.thingsService.getThings()
            .subscribe(
                (things: Thing[]) => this.things = things,
                error => console.error('ThingsComponent: cannot get things from ThingService'));
    }
}
