import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { ProduktMini } from './MODEL/ProduktMini';
import { ProduktMiniService } from './SERVICE/ProduktMini.service';
import { Tip } from './MODEL/Tip';
import { TipService } from './SERVICE/Tip.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Filter } from './MODEL/Filter';
import { Ocena } from './MODEL/Ocena';
import { Cena } from './MODEL/Cena';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'home';
  lista:ProduktMini[]|undefined;
  listaTipova: Tip[] = [];
  listaOcena: number[] = [1,2,3,4,5]
  pageSize: number;
  currentPage: number;
  totalSize: number;
  filter = <Filter>{}
  ukljucioFilter:boolean = false;
  status:boolean=false;
  status2:boolean=false;

  filterForm : FormGroup;
  tipoviForm : FormGroup;
  ocenaForm : FormGroup;

  constructor(
    private router:Router,
    private fBuilder: FormBuilder,
    private produktMiniService:ProduktMiniService,
    private tipService:TipService,
    private modalService: NgbModal
  ){
    this.pageSize = 8;
		this.currentPage = 1;
		this.totalSize = 1;

    this.filterForm = fBuilder.group({
      naziv:"",
      od:["",[this.notANumber()]],
      do:["",[this.notANumber()]]
    });

    this.tipoviForm = fBuilder.group({
      tipovi: this.fBuilder.array([])
    })

    this.ocenaForm = fBuilder.group({
      ocene: this.fBuilder.array([])
    })
  }

  ngOnInit(): void {
    this.produktMiniService.getByPage(this.currentPage - 1,this.pageSize).subscribe(
      res =>{
        this.lista = res.content as ProduktMini[];
        this.totalSize = Number(res.totalElements);
      }
    )
    
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
    this.tipoviForm.reset();
    this.ocenaForm.reset();
    this.ukljucioFilter = false;
    this.produktMiniService.getByPage(this.currentPage - 1,this.pageSize).subscribe(
      res =>{
        this.lista = res.content as ProduktMini[];
        this.totalSize = Number(res.totalElements);
        this.status2 = !this.status2
      },
      error=>{
        this.status2 = !this.status2
      }
    )
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
      this.filter.tip.push(new Tip(this.listaTipova[i].naziv,this.tipoviForm.value.tipovi.at(i).tip));
    }

    this.filter.ocena = []
    for( let i in this.listaOcena ){
      this.filter.ocena.push(new Ocena(this.listaOcena[i],this.ocenaForm.value.ocene.at(i).ocena));
    }
    this.produktMiniService.filterByPage(this.filter,this.currentPage-1,this.pageSize).subscribe(
      res =>{
        this.lista = res.body.content as ProduktMini[];
        this.totalSize = Number(res.body.totalElements);
        this.ukljucioFilter = true;
        this.status = !this.status
      },
      error=>{
        this.status = !this.status
      }
    )
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  get tipovi() : FormArray {
    return this.tipoviForm.get("tipovi") as FormArray
  }

  get ocene() : FormArray {
    return this.ocenaForm.get("ocene") as FormArray
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

  getErrorMessage(temp:any) {
    if (temp.hasError('required')) {
      return 'Morate uneti vrednost';
    }
    else if (temp.hasError('notANumber')) {
      return 'Morate uneti broj';
    }
    else
    return temp.hasError('email') ? 'To nije validan email' : '';
  }

  getErrorMessageFilter(temp:any) {
    return temp.hasError('notANumber') ? 'Morate uneti broj' : '';
  }

  changePage(newPage: number) {
    if (this.ukljucioFilter){
      this.produktMiniService.filterByPage(this.filter,newPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as ProduktMini[];
          this.totalSize = Number(res.body.totalElements);
        }
      )
    }else {
      this.produktMiniService.getByPage(newPage - 1,this.pageSize).subscribe(
        res=>{
          this.lista = res.content as ProduktMini[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }
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
