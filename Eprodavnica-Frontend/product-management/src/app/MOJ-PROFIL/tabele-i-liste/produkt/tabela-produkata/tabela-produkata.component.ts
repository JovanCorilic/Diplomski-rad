import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { AbstractControl, FormControl, ValidatorFn } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';

import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

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
  akcijaFormControl = new FormControl(0,[this.notANumber(),this.viseOdNula(),this.manjeOdSto()])
  serijskiBroj:string;

  mapa:Map<string,{name:string,retrievedImage:any}>;

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private router:Router,
    private _snackBar: MatSnackBar,
    private produktService:ProduktService,
    private modalService: NgbModal,
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
    this.mapa = new Map();
    this.serijskiBroj = "";
  }
  ngOnInit(): void {
    this.dataSource = []
    if (this.listaProdukata !== undefined)
      this.dataSource = this.listaProdukata
    
    this.mapa = new Map();
    this.listaProdukata?.forEach(element=>{
      let listaTemp = element.slika.name.split('.');
      this.mapa.set(element.serijskiBroj, {name:element.slika.name, retrievedImage:'data:image/'+listaTemp[listaTemp.length-1]+';base64,' + element.slika.picByte});
    })
  }
  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaProdukata.currentValue !== undefined)
      this.dataSource = changes.listaProdukata.currentValue

    this.mapa = new Map();
    let lista:Produkt[] = changes.listaProdukata.currentValue;
    lista?.forEach(element=>{
      let listaTemp = element.slika.name.split('.');
      this.mapa.set(element.serijskiBroj, {name:element.slika.name, retrievedImage:'data:image/'+listaTemp[listaTemp.length-1]+';base64,' + element.slika.picByte});
    })
  }

  dodajAkciju(){
    let temp:number|null = this.akcijaFormControl.value
    if (temp !== null){
      this.produktService.dodajAkciju(this.serijskiBroj,temp).subscribe(
        res=>{
          this.openSnackBar("Postavljena akcija!")
        }
      )
    }
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

  povuciProdavac(serijskiBroj:string){
    this.produktService.povuciProizvodProdavac(serijskiBroj).subscribe(
      res=>{
        this.openSnackBar("Proizvod sa serijskim brojem "+serijskiBroj+" povučen")
      }
    )
  }

  edit(serijskiBroj:string){
    this.router.navigate(['editProdukta/'+serijskiBroj])
  }

  povuciAdmin(serijskiBroj:string){
    this.produktService.povuciProizvodAdmin(serijskiBroj).subscribe(
      res=>{
        this.openSnackBar("Proizvod sa serijskim brojem "+serijskiBroj+" povučen")
      }
    )
  }

  vratiProdavac(serijskiBroj:string){
    this.produktService.vratiProizvodProdavac(serijskiBroj).subscribe(
      res=>{
        this.openSnackBar("Vraćen proizvod sa serijskim brojem "+serijskiBroj)
      }
    )
  }

  vratiAdmin(serijskiBroj:string){
    this.produktService.vratiProizvodAdmin(serijskiBroj).subscribe(
      res=>{
        this.openSnackBar("Vraćen proizvod sa serijskim brojem "+serijskiBroj)
      }
    )
  }

  getErrorMessage(temp:any) {
    if (temp.hasError('required')) {
      return 'Morate uneti vrednost';
    }
    else if (temp.hasError('notANumber')) {
      return 'Morate uneti broj';
    }
    else if(temp.hasError('manjeOdSto')){
      return 'Mora biti manje ili jednako od 100'
    }
    else
    return temp.hasError('viseOdNula') ? 'Mora biti više ili jednako od 0' : '';
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  viseOdNula():ValidatorFn{
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      if (typeof value == 'string') {
        nV = value.replace(',', '.')
      }
      return (!Number.isNaN(Number(nV)) && !control.pristine && (Number(nV)<=0)) ? {viseOdNula: true} : null;
    };
  }

  manjeOdSto():ValidatorFn{
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      if (typeof value == 'string') {
        nV = value.replace(',', '.')
      }
      return (!Number.isNaN(Number(nV)) && !control.pristine && (Number(nV)>100)) ? {viseOdNula: true} : null;
    };
  }

  notANumber(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      if (typeof value == 'string') {
        nV = value.replace(',', '.')
      }
      return (Number.isNaN(Number(nV)) && !control.pristine) ? {notANumber: true} : null;
    };
  }
}
