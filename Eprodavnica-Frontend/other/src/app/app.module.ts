import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Interceptor } from './SECURITY/Interceptor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {MatListModule} from '@angular/material/list';
import {MatDividerModule} from '@angular/material/divider';
import { NgxLoadingButtonsModule } from 'ngx-loading-buttons';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatRippleModule} from '@angular/material/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTooltipModule} from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProduktService } from './SERVICE/Produkt.service';
import { RacunService } from './SERVICE/Racun.service';
import { RecenzijaService } from './SERVICE/Recenzija.service';
import { TipService } from './SERVICE/Tip.service';
import { ProduktDetaljnoComponent } from './PRODUKT/produkt-detaljno/produkt-detaljno.component';
import { ProduktCreateComponent } from './PRODUKT/produkt-create/produkt-create.component';
import { ProduktEditComponent } from './PRODUKT/produkt-edit/produkt-edit.component';
import {MatTabsModule} from '@angular/material/tabs';
import { PaginationComponent } from './pagination/pagination.component';
import { RecenzijaTableComponent } from './RECENZIJA/recenzija-table/recenzija-table.component';
import { RecenzijaCreateComponent } from './RECENZIJA/recenzija-create/recenzija-create.component';
import {MatCardModule} from '@angular/material/card';
import {MatChipsModule} from '@angular/material/chips';
import {CdkDrag, CdkDropList} from '@angular/cdk/drag-drop';
import {MatIconModule} from '@angular/material/icon';
import { MusterijaComponent } from './MOJ-PROFIL/musterija/musterija/musterija.component';
import { ProdavacComponent } from './MOJ-PROFIL/prodavac/prodavac.component';
import { AdminComponent } from './MOJ-PROFIL/admin/admin.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { ListaRecenzijaPageComponent } from './MOJ-PROFIL/musterija/lista-recenzija-page/lista-recenzija-page.component';
import {MatTableModule} from '@angular/material/table';
import { TabelaProdukataComponent } from './MOJ-PROFIL/tabele-i-liste/produkt/tabela-produkata/tabela-produkata.component';
import { ListaProduktPageComponent } from './MOJ-PROFIL/tabele-i-liste/produkt/lista-produkt-page/lista-produkt-page.component';
import { TabelaRacunaComponent } from './MOJ-PROFIL/tabele-i-liste/racun/tabela-racuna/tabela-racuna.component';
import { ListaRacunPageComponent } from './MOJ-PROFIL/tabele-i-liste/racun/lista-racun-page/lista-racun-page.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { IndividualniRacunComponent } from './MOJ-PROFIL/tabele-i-liste/racun/individualni-racun/individualni-racun.component';
import { TabelaArtikalComponent } from './MOJ-PROFIL/tabele-i-liste/racun/tabela-artikal/tabela-artikal.component';
import { TabelaRecenzijaComponent } from './MOJ-PROFIL/musterija/lista-recenzija-page/tabela-recenzija/tabela-recenzija.component';

@NgModule({
  declarations: [
    AppComponent,
    ProduktDetaljnoComponent,
    ProduktCreateComponent,
    ProduktEditComponent,
    PaginationComponent,
    RecenzijaTableComponent,
    RecenzijaCreateComponent,
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
    TabelaRecenzijaComponent
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
    MatNativeDateModule
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
