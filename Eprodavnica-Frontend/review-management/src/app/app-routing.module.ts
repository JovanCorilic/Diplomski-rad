import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './MOJ-PROFIL/admin/admin.component';
import { MusterijaComponent } from './MOJ-PROFIL/musterija/musterija/musterija.component';

import { RoleGuard } from './SECURITY/RoleGuard';
import { EmptyRouteComponent } from './empty-route/empty-route.component';

const routes: Routes = [
  {
    path: 'review-management/admin',
    component:AdminComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMIN'}
  },
  {
    path: 'review-management/musterija',
    component:MusterijaComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_MUSTERIJA'}
  },
  
  { path: '**', component: EmptyRouteComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash : true})],
  providers: [{provide: APP_BASE_HREF, useValue:'/'}],
  exports: [RouterModule]
})
export class AppRoutingModule { }
