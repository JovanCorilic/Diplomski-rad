import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './MOJ-PROFIL/admin/admin.component';
import { RoleGuard } from './SECURITY/RoleGuard';
import { EmptyRouteComponent } from './empty-route/empty-route.component';

const routes: Routes = [
  {
    path: 'admin',
    component:AdminComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMIN'}
  },
  { path: '**', component: EmptyRouteComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash : true})],
  providers: [{provide: APP_BASE_HREF, useValue:'/category-management'}],
  exports: [RouterModule]
})
export class AppRoutingModule { }
