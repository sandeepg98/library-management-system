import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BookingComponentComponent } from '../app/components/booking-component/booking-component.component';
import { ReturningComponentComponent } from '../app/components/returning-component/returning-component.component';
import { HomeComponent } from '../app/components/home/home.component';

const routes: Routes = [
  {
    path: 'booking',component: BookingComponentComponent
  },
  {
    path: 'returning',component: ReturningComponentComponent
  },
  {
    path: 'home',component: HomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
