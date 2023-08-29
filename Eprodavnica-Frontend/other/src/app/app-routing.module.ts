import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmptyRouteComponent } from './empty-route/empty-route.component';
import { ProduktDetaljnoComponent } from './PRODUKT/produkt-detaljno/produkt-detaljno.component';
import { ProduktCreateComponent } from './PRODUKT/produkt-create/produkt-create.component';
import { RoleGuard } from './SECURITY/RoleGuard';
import { ProduktEditComponent } from './PRODUKT/produkt-edit/produkt-edit.component';

const routes: Routes = [
  { path: '**', component: EmptyRouteComponent},
  {
    path: '**/produktDetaljno/:serijskiBroj',
    component: ProduktDetaljnoComponent
  },
  {
    path: '**/pravljenjeProdukta',
    component:ProduktCreateComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_PRODAVAC'}
  },
  {
    path: '**/editProdukta/:serijskiBroj',
    component:ProduktEditComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_PRODAVAC|ROLE_ADMIN'}
  }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  providers: [{provide: APP_BASE_HREF, useValue:'/'}],
  exports: [RouterModule]
})
export class AppRoutingModule { }
