import { Component, Input } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Cena } from 'src/app/MODEL/Filter/Cena';
import { Datum } from 'src/app/MODEL/Filter/Datum';
import { Filter } from 'src/app/MODEL/Filter/Filter';
import { Ocena } from 'src/app/MODEL/Filter/Ocena';
import { Produkt } from 'src/app/MODEL/Produkt';
import { Racun } from 'src/app/MODEL/Racun';
import { Tip } from 'src/app/MODEL/Tip';
import { RacunService } from 'src/app/SERVICE/Racun.service';

@Component({
  selector: 'app-lista-racun-page',
  templateUrl: './lista-racun-page.component.html',
  styleUrls: ['./lista-racun-page.component.css']
})
export class ListaRacunPageComponent {
  @Input() svrha:string|undefined;

  lista:Racun[]|undefined;
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
    private modalService: NgbModal,
    private racunService:RacunService
  ){
    this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;

    this.filterForm = fBuilder.group({
      od:["",[this.notANumber()]],
      do:["",[this.notANumber()]],
      odDatum:"",
      doDatum:""
    });

  }

  ngOnInit(): void {
    if (this.svrha === "Musterija")
      this.racunService.getByPageMusterija(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Racun[];
          this.totalSize = Number(res.totalElements);
        }
      )
    else if(this.svrha === "Admin"){
      this.racunService.getByPageAdmin(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Racun[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }
  }

  resetuj(){
    this.status2 = !this.status2
    this.filter = <Filter>{}
    this.filterForm.reset();

    this.ukljucioFilter = false;

    if (this.svrha === "Musterija")
      this.racunService.getByPageMusterija(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Racun[];
          this.totalSize = Number(res.totalElements);
          this.status2 = !this.status2
        },
        error=>{
          this.status2 = !this.status2
        }
      )
    else if(this.svrha === "Admin")
      this.racunService.getByPageAdmin(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Racun[];
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

    this.filter.cena = <Cena>{};
    if( this.filterForm.value.od != "")
      this.filter.cena.odCena = this.filterForm.value.od;
    if( this.filterForm.value.do != "")
      this.filter.cena.doCena = this.filterForm.value.do;

    this.filter.datum = <Datum>{};
    if (this.filterForm.value.odDatum != "")
      this.filter.datum.odDatum = this.filterForm.value.odDatum;
    if (this.filterForm.value.doDatum != "")
      this.filter.datum.doDatum = this.filterForm.value.doDatum;


    if (this.svrha === "Musterija")
      this.racunService.filterByPageMusterija(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Racun[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status
        },
        error=>{
          this.status = !this.status
        }
      )
    else if(this.svrha === "Admin")
      this.racunService.filterByPageAdmin(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Racun[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status
        },
        error=>{
          this.status = !this.status
        }
      )
  }

  changePage(newPage: number) {
    if (this.svrha === "Musterija")
      if (this.ukljucioFilter){
        this.racunService.filterByPageMusterija(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Racun[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.racunService.getByPageMusterija(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Racun[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
    else if(this.svrha === "Admin")
      if (this.ukljucioFilter){
        this.racunService.filterByPageAdmin(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Racun[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.racunService.getByPageAdmin(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Racun[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
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
