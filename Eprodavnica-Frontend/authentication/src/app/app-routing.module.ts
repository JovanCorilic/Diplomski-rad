import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmptyRouteComponent } from './empty-route/empty-route.component';
import { APP_BASE_HREF } from '@angular/common';
import { VerifikacijaMusterijaProdavacRegistracijaComponent } from './SECURITY/verifikacija-musterija-prodavac-registracija/verifikacija-musterija-prodavac-registracija.component';
import { VerifikacijaAdminRegistracijaComponent } from './SECURITY/verifikacija-admin-registracija/verifikacija-admin-registracija.component';
import { PregledComponent } from './pregled/pregled.component';

const routes: Routes = [
  {
    path: 'navbar/verifikacijaRegistracija/:token',
    component:VerifikacijaMusterijaProdavacRegistracijaComponent
  },
  {
    path: 'navbar/VerifikacijaAdminPravljenje/:token',
    component: VerifikacijaAdminRegistracijaComponent
  },

  { path: '**', component: EmptyRouteComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash : true})],
  providers: [{provide: APP_BASE_HREF, useValue:'/'}],
  exports: [RouterModule]
})
export class AppRoutingModule { }
