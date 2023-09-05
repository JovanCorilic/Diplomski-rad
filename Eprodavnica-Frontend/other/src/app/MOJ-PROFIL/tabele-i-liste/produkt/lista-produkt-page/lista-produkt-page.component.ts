import { Component, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, ValidatorFn, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Cena } from 'src/app/MODEL/Filter/Cena';
import { Filter } from 'src/app/MODEL/Filter/Filter';
import { Ocena } from 'src/app/MODEL/Filter/Ocena';
import { TipFilter } from 'src/app/MODEL/Filter/TipFilter';
import { Produkt } from 'src/app/MODEL/Produkt';
import { Tip } from 'src/app/MODEL/Tip';
import { ProduktService } from 'src/app/SERVICE/Produkt.service';
import { TipService } from 'src/app/SERVICE/Tip.service';

@Component({
  selector: 'app-lista-produkt-page',
  templateUrl: './lista-produkt-page.component.html',
  styleUrls: ['./lista-produkt-page.component.css']
})
export class ListaProduktPageComponent {
  @Input() svrha:string|undefined;

  lista:Produkt[]|undefined;
  listaTipova: TipFilter[] = [];
  listaOcena: number[] = [1,2,3,4,5]
  pageSize: number;
  currentPage: number;
  totalSize: number;
  filter = <Filter>{}
  ukljucioFilter:boolean = false;
  status:boolean=false;
  status2:boolean=false;

  filterForm : FormGroup;

  constructor(
    private router:Router,
    private fBuilder: FormBuilder,
    private produktService:ProduktService,
    private tipService:TipService,
    private modalService: NgbModal
  ){
    this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;

    this.filterForm = fBuilder.group({
      naziv:"",
      od:["",[this.notANumber()]],
      do:["",[this.notANumber()]],
      tipovi: this.fBuilder.array([]),
      ocene: this.fBuilder.array([])
    });

  }

  ngOnInit(): void {
    if (this.svrha === "Wishlist")
      this.produktService.getByPageWishlist(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Produkt[];
          this.totalSize = Number(res.totalElements);
        }
      )
    else if(this.svrha === "IstorijaProdukata"){
      this.produktService.getByPageIstorijaProdukata(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Produkt[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }
    else if(this.svrha === "Prodavac"){
      this.produktService.getByPageProdavac(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Produkt[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }

    this.tipService.getAllTip().subscribe(
      res=>{
        this.listaTipova = res;
        for( let i in this.listaTipova ){
          this.addTip();
        }
      }
    )

    for (let i in this.listaOcena){
      this.addOcena();
    }
  }

  resetuj(){
    this.status2 = !this.status2
    this.filter = <Filter>{}
    this.filterForm.reset();

    this.ukljucioFilter = false;

    if (this.svrha === "Wishlist")
      this.produktService.getByPageIstorijaProdukata(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Produkt[];
          this.totalSize = Number(res.totalElements);
          this.status2 = !this.status2
        },
        error=>{
          this.status2 = !this.status2
        }
      )
    else if(this.svrha === "IstorijaProdukata")
      this.produktService.getByPageWishlist(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Produkt[];
          this.totalSize = Number(res.totalElements);
          this.status2 = !this.status2
        },
        error=>{
          this.status2 = !this.status2
        }
      )
    else if(this.svrha === "Prodavac"){
      this.produktService.getByPageProdavac(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Produkt[];
          this.totalSize = Number(res.totalElements);
          this.status2 = !this.status2
        },
        error=>{
          this.status2 = !this.status2
        }
      )
    }
  }

  filters(){
    this.status = !this.status
    if( this.filterForm.value.naziv != "")
      this.filter.naziv = this.filterForm.value.naziv;

    this.filter.cena = <Cena>{};
    if( this.filterForm.value.od != "")
      this.filter.cena.odCena = this.filterForm.value.od;
    if( this.filterForm.value.do != "")
      this.filter.cena.doCena = this.filterForm.value.do;

    this.filter.tip = []
    for( let i in this.listaTipova ){
      this.filter.tip.push(new Tip(this.listaTipova[i].naziv,this.filterForm.value.tipovi.at(i).tip));
    }

    this.filter.ocena = []
    for( let i in this.listaOcena ){
      this.filter.ocena.push(new Ocena(this.listaOcena[i],this.filterForm.value.ocene.at(i).ocena));
    }

    if (this.svrha === "Wishlist")
      this.produktService.filterByPageWishlist(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Produkt[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status
        },
        error=>{
          this.status = !this.status
        }
      )
    else if(this.svrha === "IstorijaProdukata")
      this.produktService.filterByPageIstorijaProdukata(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Produkt[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status
        },
        error=>{
          this.status = !this.status
        }
      )
    else if(this.svrha === "Prodavac"){
      this.produktService.filterByPageProdavac(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Produkt[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status
        },
        error=>{
          this.status = !this.status
        }
      )
    }
  }

  changePage(newPage: number) {
    if (this.svrha === "Wishlist")
      if (this.ukljucioFilter){
        this.produktService.filterByPageWishlist(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Produkt[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.produktService.getByPageWishlist(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Produkt[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
    else if(this.svrha === "IstorijaProdukata")
      if (this.ukljucioFilter){
        this.produktService.filterByPageIstorijaProdukata(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Produkt[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.produktService.getByPageIstorijaProdukata(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Produkt[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
    else if(this.svrha === "Prodavac")
    if (this.ukljucioFilter){
      this.produktService.filterByPageProdavac(this.filter,newPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Produkt[];
          this.totalSize = Number(res.body.totalElements);
        }
      )
    }else {
      this.produktService.getByPageProdavac(newPage - 1,this.pageSize).subscribe(
        res=>{
          this.lista = res.content as Produkt[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  get tipovi() : FormArray {
    return this.filterForm.get("tipovi") as FormArray
  }

  get ocene() : FormArray {
    return this.filterForm.get("ocene") as FormArray
  }

  newTip(): FormGroup {
    return this.fBuilder.group({
      tip:false
    })
  }

  newOcena(): FormGroup {
    return this.fBuilder.group({
      ocena:false
    })
  }
 
  addTip() {
    this.tipovi.push(this.newTip());
  }

  addOcena(){
    this.ocene.push(this.newOcena());
  }

  getErrorMessageFilter(temp:any) {
    return temp.hasError('notANumber') ? 'Morate uneti broj' : '';
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
