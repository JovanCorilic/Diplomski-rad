import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Recenzija } from 'src/app/MODEL/Recenzija';
import { RecenzijaService } from 'src/app/SERVICE/Recenzija.service';

@Component({
  selector: 'app-tabela-recenzija',
  templateUrl: './tabela-recenzija.component.html',
  styleUrls: ['./tabela-recenzija.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class TabelaRecenzijaComponent implements OnInit, OnChanges{
  @Input() listaRecenzija:Recenzija[]|undefined;

  dataSource:Recenzija[] = []
  columnsToDisplay = ['ocena', 'datumPravljenja', 'produktNaziv'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement = <Recenzija>{}

  mapa:Map<string,{name:string,retrievedImage:any}>;

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  @ViewChild(MatTable) table: MatTable<Recenzija> | undefined;

  constructor(
    private router:Router,
    private recenzijaService:RecenzijaService,
    private fBuilder: FormBuilder,
    private modalService: NgbModal,
    private _snackBar: MatSnackBar
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
    this.mapa = new Map();
  }

  ngOnInit(): void {
    this.dataSource = []
    if (this.listaRecenzija !==undefined){
      this.dataSource = this.listaRecenzija;
    }

    this.mapa = new Map();
    this.listaRecenzija?.forEach(element=>{
      let listaTemp = element.produkt.slika.name.split('.');
      this.mapa.set(element.produkt.serijskiBroj, {name:element.produkt.slika.name, retrievedImage:'data:image/'+listaTemp[listaTemp.length-1]+';base64,' + element.produkt.slika.picByte});
    })
  }

  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaRecenzija.currentValue !== undefined){
      this.dataSource = changes.listaRecenzija.currentValue
    }

    this.mapa = new Map();
    let lista:Recenzija[] = changes.listaRecenzija.currentValue;
    lista?.forEach(element=>{
      let listaTemp = element.produkt.slika.name.split('.');
      this.mapa.set(element.produkt.serijskiBroj, {name:element.produkt.slika.name, retrievedImage:'data:image/'+listaTemp[listaTemp.length-1]+';base64,' + element.produkt.slika.picByte});
    })
  }

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['produktDetaljno/'+serijskiBroj])
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

  ukloni(id:number){
    this.recenzijaService.ukloniRecenziju(id).subscribe(
      res=>{
        this.removeData(id);
        this.openSnackBar("Uspešno uklonjena recenzija")
      }
    )
  }

  getRole():string{
    const item = sessionStorage.getItem('user');

    if(!item){
      return "";
    }

    const jwt:JwtHelperService = new JwtHelperService();
    const decodedItem = JSON.parse(item!);
    const info = jwt.decodeToken(decodedItem.accessToken);
    return info['uloga'];
  }

}
