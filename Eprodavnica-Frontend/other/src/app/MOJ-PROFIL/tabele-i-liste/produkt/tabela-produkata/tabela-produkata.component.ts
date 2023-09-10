import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';

import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

import { Produkt } from 'src/app/MODEL/Produkt';
import { ProduktService } from 'src/app/SERVICE/Produkt.service';


@Component({
  selector: 'app-tabela-produkata',
  templateUrl: './tabela-produkata.component.html',
  styleUrls: ['./tabela-produkata.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class TabelaProdukataComponent implements OnInit, OnChanges{
  @Input() listaProdukata:Produkt[]|undefined;

  dataSource:Produkt[] = []
  columnsToDisplay = ['naziv', 'cena', 'ocena'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement = <Produkt>{}

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private router:Router,
    private _snackBar: MatSnackBar,
    private produktService:ProduktService
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }
  ngOnInit(): void {
    this.dataSource = []
    if (this.listaProdukata !== undefined)
      this.dataSource = this.listaProdukata
  }
  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaProdukata.currentValue !== undefined)
      this.dataSource = changes.listaProdukata.currentValue
  }

  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['produktDetaljno/'+serijskiBroj])
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

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  povuci(serijskiBroj:string){
    this.produktService.povuciProizvod(serijskiBroj).subscribe(
      res=>{
        this.openSnackBar("Proizvod sa serijskim brojem "+serijskiBroj+" povučen")
      }
    )
  }

  vrati(serijskiBroj:string){
    this.produktService.vratiProizvod(serijskiBroj).subscribe(
      res=>{
        this.openSnackBar("Vraćen proizvod sa serijskim brojem "+serijskiBroj)
      }
    )
  }
}
