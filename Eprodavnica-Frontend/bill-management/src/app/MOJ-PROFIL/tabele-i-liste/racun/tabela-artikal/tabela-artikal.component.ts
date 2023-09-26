import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { Artikal } from 'src/app/MODEL/Artikal';
import { RacunService } from 'src/app/SERVICE/Racun.service';

@Component({
  selector: 'app-tabela-artikal',
  templateUrl: './tabela-artikal.component.html',
  styleUrls: ['./tabela-artikal.component.css']
})
export class TabelaArtikalComponent implements OnInit, OnChanges{
  @Input() listaArtikala:Artikal[]|undefined;
  @Input() korpa:boolean|undefined;
  @Output() ukupnaCena: EventEmitter<number>;

  dataSource:Artikal[] = []
  displayedColumns = ['nazivProdukta','cena','akcija','broj','ukupnaCena','ukloni'];

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  @ViewChild(MatTable)
  table!: MatTable<Artikal>;

  constructor(
    private router:Router,
    private _snackBar: MatSnackBar,
    private racunService:RacunService
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
    this.ukupnaCena = new EventEmitter();
  }

  ngOnInit(): void {
    this.dataSource = []
    if (this.listaArtikala !== undefined)
      this.dataSource = this.listaArtikala
  }
  
  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaArtikala !== undefined)
      if (changes.listaArtikala.currentValue !==undefined)
        this.dataSource = changes.listaArtikala.currentValue
  }

  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['produktDetaljno/'+serijskiBroj])
  }

  ukloni(id:number,cena:number){
    this.racunService.ukloniArtikal(id).subscribe(
      res=>{
        this.removeData(id);
        this.openSnackBar("Uspešno uklonjen artikal")
        this.ukupnaCena.emit(cena);
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
