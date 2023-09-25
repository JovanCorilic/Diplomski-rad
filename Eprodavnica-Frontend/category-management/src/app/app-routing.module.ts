import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './MOJ-PROFIL/admin/admin.component';
import { MusterijaComponent } from './MOJ-PROFIL/musterija/musterija/musterija.component';
import { ProdavacComponent } from './MOJ-PROFIL/prodavac/prodavac.component';
import { SuperadminComponent } from './MOJ-PROFIL/superadmin/superadmin.component';
import { IndividualniRacunComponent } from './MOJ-PROFIL/tabele-i-liste/racun/individualni-racun/individualni-racun.component';
import { RoleGuard } from './SECURITY/RoleGuard';
import { EmptyRouteComponent } from './empty-route/empty-route.component';

const routes: Routes = [
  {
    path: 'admin',
    component:AdminComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMIN'}
  },
  {
    path: 'musterija',
    component:MusterijaComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_MUSTERIJA'}
  },
  {
    path:'prodavac',
    component:ProdavacComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_PRODAVAC'}
  },
  {
    path:'superadmin',
    component:SuperadminComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_SUPERADMIN'}
  },
  {
    path:'racun/:brojRacuna',
    component:IndividualniRacunComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_MUSTERIJA|ROLE_ADMIN'}
  },
  { path: '**', component: EmptyRouteComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash : true})],
  providers: [{provide: APP_BASE_HREF, useValue:'/category-management'}],
  exports: [RouterModule]
})
export class AppRoutingModule { }
