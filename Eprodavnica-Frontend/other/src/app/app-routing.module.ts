import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmptyRouteComponent } from './empty-route/empty-route.component';
import { ProduktDetaljnoComponent } from './PRODUKT/produkt-detaljno/produkt-detaljno.component';

const routes: Routes = [
  { path: '**', component: EmptyRouteComponent},
  {
    path: '**/produktDetaljno/:serijskiBroj',
    component: ProduktDetaljnoComponent
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  providers: [{provide: APP_BASE_HREF, useValue:'/'}],
  exports: [RouterModule]
})
export class AppRoutingModule { }
