import { MatToolbarModule } from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { Interceptor } from './SECURITY/Interceptor';
import { AuthenticationService } from './SERVICE/authentication.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatInputModule } from '@angular/material/input';
import { NgxLoadingButtonsModule } from 'ngx-loading-buttons';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { VerifikacijaAdminRegistracijaComponent, VerifikacijaAdminRegistracijaComponentDialog } from './SECURITY/verifikacija-admin-registracija/verifikacija-admin-registracija.component';
import { VerifikacijaMusterijaProdavacRegistracijaComponent, VerifikacijaMusterijaProdavacRegistracijaComponentDialog } from './SECURITY/verifikacija-musterija-prodavac-registracija/verifikacija-musterija-prodavac-registracija.component';
import {MatSelectModule} from '@angular/material/select';
import {MatDialogModule} from '@angular/material/dialog';
import { PromenaLozinkeComponent, PromenaLozinkeDialog } from './SECURITY/promena-lozinke/promena-lozinke.component';

@NgModule({
  declarations: [
    AppComponent,
    VerifikacijaAdminRegistracijaComponent,
    VerifikacijaMusterijaProdavacRegistracijaComponent,
    VerifikacijaMusterijaProdavacRegistracijaComponentDialog,
    VerifikacijaAdminRegistracijaComponentDialog,
    PromenaLozinkeComponent,
    PromenaLozinkeDialog
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatFormFieldModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatIconModule,
    NgbModule,
    MatInputModule,
    NgxLoadingButtonsModule,
    MatSnackBarModule,
    MatSelectModule,
    MatDialogModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, 
      useClass: Interceptor, 
      multi: true
    },
    AuthenticationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
