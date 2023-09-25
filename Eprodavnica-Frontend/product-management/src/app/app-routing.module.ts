import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmptyRouteComponent } from './empty-route/empty-route.component';
import { ProduktDetaljnoComponent } from './PRODUKT/produkt-detaljno/produkt-detaljno.component';
import { ProduktCreateComponent } from './PRODUKT/produkt-create/produkt-create.component';
import { RoleGuard } from './SECURITY/RoleGuard';
import { ProduktEditComponent } from './PRODUKT/produkt-edit/produkt-edit.component';
import { AdminComponent } from './MOJ-PROFIL/admin/admin.component';
import { MusterijaComponent } from './MOJ-PROFIL/musterija/musterija/musterija.component';
import { ProdavacComponent } from './MOJ-PROFIL/prodavac/prodavac.component';

const routes: Routes = [
  {
    path: 'produktDetaljno/:serijskiBroj',
    component: ProduktDetaljnoComponent
  },
  {
    path: 'pravljenjeProdukta',
    component:ProduktCreateComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_PRODAVAC'}
  },
  {
    path: 'editProdukta/:serijskiBroj',
    component:ProduktEditComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_PRODAVAC|ROLE_ADMIN'}
  },
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

  { path: '**', component: EmptyRouteComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash : true})],
  providers: [{provide: APP_BASE_HREF, useValue:'/product-management'}],
  exports: [RouterModule]
})
export class AppRoutingModule { }
