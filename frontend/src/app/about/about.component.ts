import {Component, OnInit} from '@angular/core';
import {AboutService} from "./about.service";

@Component({
    selector: 'about',
    templateUrl: 'about.component.html',
    styleUrls: ['about.component.css'],
    providers: [AboutService]
})
export class AboutComponent implements OnInit {
    version: string;
    time: string;

    constructor(private aboutService: AboutService) {
    }

    ngOnInit() {
        this.aboutService.getBuildInfo().subscribe(
            (response: any) => {
                this.version = response.build.version;
                this.time = response.build.time;
            },
            error => console.error('AboutComponent: cannot get build info from AboutService')
        );
    }

}
