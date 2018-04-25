import { Routes } from '@angular/router';

import {ThingsComponent} from './thing/things.component';
import {AboutComponent} from './about/about.component';

export const routerConfig: Routes = [
    { path: '', redirectTo: 'things', pathMatch: 'full' },
    { path: 'things', component: ThingsComponent },
    { path: 'about', component: AboutComponent }
];
