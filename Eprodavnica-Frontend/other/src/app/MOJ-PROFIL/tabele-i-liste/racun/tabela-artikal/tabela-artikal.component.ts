import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { Artikal } from 'src/app/MODEL/Artikal';
import { Recenzija } from 'src/app/MODEL/Recenzija';
import { RacunService } from 'src/app/SERVICE/Racun.service';

@Component({
  selector: 'app-tabela-artikal',
  templateUrl: './tabela-artikal.component.html',
  styleUrls: ['./tabela-artikal.component.css']
})
export class TabelaArtikalComponent implements OnInit, OnChanges{
  @Input() listaArtikala:Artikal[]|undefined;
  @Input() korpa:boolean|undefined;

  dataSource:Artikal[] = []
  displayedColumns = ['nazivProdukta','cena','akcija','broj','ukupnaCena','ukloni'];
  expandedElement = <Artikal>{}
  deoKorpe:boolean = false;

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  @ViewChild(MatTable) table: MatTable<Artikal> | undefined;

  constructor(
    private router:Router,
    private _snackBar: MatSnackBar,
    private racunService:RacunService
  ){
    if (this.listaArtikala !== undefined)
      this.dataSource = this.listaArtikala
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }
  ngOnInit(): void {
    this.dataSource = []
    if (this.listaArtikala !== undefined)
      this.dataSource = this.listaArtikala
    if (this.korpa !== undefined)
      this.deoKorpe = this.korpa
  }
  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaArtikala.currentValue !==undefined)
      this.dataSource = changes.listaArtikala.currentValue
    if (changes.korpa.currentValue !== undefined)
      this.deoKorpe = changes.korpa.currentValue
  }

  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['produktDetaljno/'+serijskiBroj])
  }

  ukloni(id:number){
    this.racunService.ukloniArtikal(id).subscribe(
      res=>{
        this.removeData(id);
        this.openSnackBar("Uspešno uklonjen artikal")
      }
    )
  }

  removeData(id:number) {
    for ( let i in this.dataSource){
      if(this.dataSource[i].id === id){
        this.dataSource.splice( Number.parseInt(i) , 1 );
        break;
      }
    }
    if (this.table !== undefined)
      this.table.renderRows();
  }

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}
