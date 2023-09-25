import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxLoadingButtonsModule } from 'ngx-loading-buttons';
import { Interceptor } from './SECURITY/Interceptor';
import { ProduktService } from './SERVICE/Produkt.service';
import { RacunService } from './SERVICE/Racun.service';
import { RecenzijaService } from './SERVICE/Recenzija.service';
import { TipService } from './SERVICE/Tip.service';

import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatTableModule} from '@angular/material/table';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatCardModule} from '@angular/material/card';
import {MatChipsModule} from '@angular/material/chips';
import {CdkDrag, CdkDropList} from '@angular/cdk/drag-drop';
import {MatIconModule} from '@angular/material/icon';
import {MatTabsModule} from '@angular/material/tabs';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatRippleModule} from '@angular/material/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTooltipModule} from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatListModule} from '@angular/material/list';
import {MatDividerModule} from '@angular/material/divider';
import { AdminComponent } from './MOJ-PROFIL/admin/admin.component';
import { MusterijaComponent } from './MOJ-PROFIL/musterija/musterija/musterija.component';
import { ProdavacComponent } from './MOJ-PROFIL/prodavac/prodavac.component';
import { SuperadminComponent } from './MOJ-PROFIL/superadmin/superadmin.component';
import { ListaKorisnikPageComponent } from './MOJ-PROFIL/tabele-i-liste/korisnik/lista-korisnik-page/lista-korisnik-page.component';
import { TabelaKorisnikComponent } from './MOJ-PROFIL/tabele-i-liste/korisnik/tabela-korisnik/tabela-korisnik.component';
import { ListaProduktPageComponent } from './MOJ-PROFIL/tabele-i-liste/produkt/lista-produkt-page/lista-produkt-page.component';
import { TabelaProdukataComponent } from './MOJ-PROFIL/tabele-i-liste/produkt/tabela-produkata/tabela-produkata.component';
import { IndividualniRacunComponent } from './MOJ-PROFIL/tabele-i-liste/racun/individualni-racun/individualni-racun.component';
import { ListaRacunPageComponent } from './MOJ-PROFIL/tabele-i-liste/racun/lista-racun-page/lista-racun-page.component';
import { TabelaArtikalComponent } from './MOJ-PROFIL/tabele-i-liste/racun/tabela-artikal/tabela-artikal.component';
import { TabelaRacunaComponent } from './MOJ-PROFIL/tabele-i-liste/racun/tabela-racuna/tabela-racuna.component';
import { ListaRecenzijaPageComponent } from './MOJ-PROFIL/tabele-i-liste/recenzija/lista-recenzija-page/lista-recenzija-page.component';
import { TabelaRecenzijaComponent } from './MOJ-PROFIL/tabele-i-liste/recenzija/tabela-recenzija/tabela-recenzija.component';
import { ListaTipPageComponent } from './MOJ-PROFIL/tabele-i-liste/tip/lista-tip-page/lista-tip-page.component';
import { TabelaTipComponent } from './MOJ-PROFIL/tabele-i-liste/tip/tabela-tip/tabela-tip.component';
import { PaginationComponent } from './pagination/pagination.component';

@NgModule({
  declarations: [
    AppComponent,
    PaginationComponent,
    MusterijaComponent,
    ProdavacComponent,
    AdminComponent,
    ListaRecenzijaPageComponent,
    TabelaProdukataComponent,
    ListaProduktPageComponent,
    TabelaRacunaComponent,
    ListaRacunPageComponent,
    IndividualniRacunComponent,
    TabelaArtikalComponent,
    TabelaRecenzijaComponent,
    ListaKorisnikPageComponent,
    TabelaKorisnikComponent,
    SuperadminComponent,
    ListaTipPageComponent,
    TabelaTipComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatFormFieldModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatTooltipModule,
    MatRippleModule,
    MatSidenavModule,
    MatButtonModule,
    MatInputModule,
    MatListModule,
    MatDividerModule,
    NgbModule,
    NgxLoadingButtonsModule,
    MatCheckboxModule,
    MatTabsModule,
    MatCardModule,
    MatChipsModule,
    CdkDropList,
    CdkDrag,
    MatIconModule,
    MatSnackBarModule,
    MatTableModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatToolbarModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, 
      useClass: Interceptor, 
      multi: true
    },
    ProduktService,
    RacunService,
    RecenzijaService,
    TipService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
