import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Datum } from 'src/app/MODEL/Filter/Datum';
import { Filter } from 'src/app/MODEL/Filter/Filter';
import { Ocena } from 'src/app/MODEL/Filter/Ocena';
import { Recenzija } from 'src/app/MODEL/Recenzija';
import { RecenzijaService } from 'src/app/SERVICE/Recenzija.service';

@Component({
  selector: 'app-lista-recenzija-page',
  templateUrl: './lista-recenzija-page.component.html',
  styleUrls: ['./lista-recenzija-page.component.css']
})
export class ListaRecenzijaPageComponent implements OnInit{
  @Input() svrha:string|undefined;

  lista:Recenzija[]|undefined;
  listaOcena: number[] = [1,2,3,4,5]
  filter = <Filter>{}
  ukljucioFilter:boolean = false;
  status:boolean=false;
  status2:boolean = false

  filterForm : FormGroup;

  pageSize: number;
  currentPage: number;
  totalSize: number;

  constructor(
    private fBuilder: FormBuilder,
    private modalService: NgbModal,
    private recenzijaService:RecenzijaService
  ){
    this.pageSize = 6;
		this.currentPage = 1;
		this.totalSize = 1;

    this.filterForm = fBuilder.group({
      ocene: this.fBuilder.array([]),
      odDatum:"",
      doDatum:""
    });

  }

  ngOnInit(): void {
    if (this.svrha === "Musterija"){
      this.recenzijaService.getByPageMusterija(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Recenzija[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }
    else if(this.svrha === "Admin"){
      this.recenzijaService.getByPageAdmin(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Recenzija[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }

    for (let i in this.listaOcena){
      this.addOcena();
    }
  }

  filters(){
    this.status = !this.status;
    this.filter.ocena = []
    for( let i in this.listaOcena ){
      this.filter.ocena.push(new Ocena(this.listaOcena[i],this.filterForm.value.ocene.at(i).ocena));
    }

    this.filter.datum = <Datum>{}
    if (this.filterForm.value.odDatum != "")
      this.filter.datum.odDatum = this.filterForm.value.odDatum;
    if (this.filterForm.value.doDatum != "")
      this.filter.datum.doDatum = this.filterForm.value.doDatum;

    if (this.svrha === "Musterija"){
      this.recenzijaService.filterByPageMusterija(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Recenzija[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status;
        },
        error =>{
          this.status = !this.status;
        }
      )
    }
    else if(this.svrha === "Admin"){
      this.recenzijaService.filterByPageAdmin(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Recenzija[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status;
        },
        error =>{
          this.status = !this.status;
        }
      )
    }
  }

  changePage(newPage: number) {
    if (this.svrha === "Musterija"){
      if (this.ukljucioFilter){
        this.recenzijaService.filterByPageMusterija(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Recenzija[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.recenzijaService.getByPageMusterija(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Recenzija[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
    }
    else if(this.svrha === "Admin"){
      if (this.ukljucioFilter){
        this.recenzijaService.filterByPageAdmin(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Recenzija[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.recenzijaService.getByPageAdmin(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Recenzija[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
    }
  }

  resetuj(){
    this.status2 = !this.status2
    this.filter = <Filter>{}
    this.filterForm.reset();
    this.ukljucioFilter = false;
    if (this.svrha === "Musterija"){
      this.recenzijaService.getByPageMusterija(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.status2 = !this.status2
          this.lista = res.content as Recenzija[];
          this.totalSize = Number(res.totalElements);
        },
        error=>{
          this.status2 = !this.status2
        }
      )
    }
    else if(this.svrha === "Admin"){
      this.recenzijaService.getByPageAdmin(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.status2 = !this.status2
          this.lista = res.content as Recenzija[];
          this.totalSize = Number(res.totalElements);
        },
        error=>{
          this.status2 = !this.status2
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

  get ocene() : FormArray {
    return this.filterForm.get("ocene") as FormArray
  }

  newOcena(): FormGroup {
    return this.fBuilder.group({
      ocena:false
    })
  }

  addOcena(){
    this.ocene.push(this.newOcena());
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
