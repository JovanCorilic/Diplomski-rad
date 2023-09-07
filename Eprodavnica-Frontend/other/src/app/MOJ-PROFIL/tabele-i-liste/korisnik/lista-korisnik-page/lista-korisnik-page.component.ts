import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Korisnik } from 'src/app/MODEL/Korisnik';
import { KorisnikService } from 'src/app/SERVICE/Korisnik.service';

@Component({
  selector: 'app-lista-korisnik-page',
  templateUrl: './lista-korisnik-page.component.html',
  styleUrls: ['./lista-korisnik-page.component.css']
})
export class ListaKorisnikPageComponent implements OnInit{
  @Input() svrha:string|undefined;

  pageSize: number;
  currentPage: number;
  totalSize: number;

  filter = <Korisnik>{}
  ukljucioFilter:boolean = false;
  status:boolean=false;
  status2:boolean=false;

  lista:Korisnik[]|undefined;

  filterForm : FormGroup;

  constructor(
    private fBuilder: FormBuilder,
    private korisnikService:KorisnikService
  ){
    this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;

    this.filterForm = fBuilder.group({
      email:"",
      ime:"",
      prezime:""
    });
  }

  ngOnInit(): void {
    if (this.svrha === "Musterija"){
      this.korisnikService.getByPageMusterija(this.currentPage - 1,this.pageSize).subscribe(
        res=>{
          this.lista = res.content as Korisnik[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }
    else if (this.svrha === "Prodavac"){
      this.korisnikService.getByPageProdavac(this.currentPage - 1,this.pageSize).subscribe(
        res=>{
          this.lista = res.content as Korisnik[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }
    else if(this.svrha = "Admin"){
      this.korisnikService.getByPageAdmin(this.currentPage - 1,this.pageSize).subscribe(
        res=>{
          this.lista = res.content as Korisnik[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }

  }

  resetuj(){
    this.status2 = !this.status2
    this.filter = <Korisnik>{}
    this.filterForm.reset();

    this.ukljucioFilter = false;

    if (this.svrha === "Musterija")
      this.korisnikService.getByPageMusterija(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Korisnik[];
          this.totalSize = Number(res.totalElements);
          this.status2 = !this.status2
        },
        error=>{
          this.status2 = !this.status2
        }
      )
    else if(this.svrha === "Prodavac")
      this.korisnikService.getByPageProdavac(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Korisnik[];
          this.totalSize = Number(res.totalElements);
          this.status2 = !this.status2
        },
        error=>{
          this.status2 = !this.status2
        }
      )
    else if(this.svrha === "Admin")
      this.korisnikService.getByPageAdmin(this.currentPage - 1,this.pageSize).subscribe(
        res =>{
          this.lista = res.content as Korisnik[];
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
    if( this.filterForm.value.email != "")
      this.filter.email = this.filterForm.value.email;

    if( this.filterForm.value.ime != "")
      this.filter.ime = this.filterForm.value.ime;

    if( this.filterForm.value.prezime != "")
      this.filter.prezime = this.filterForm.value.prezime;

    if (this.svrha === "Musterija")
      this.korisnikService.filterByPageMusterija(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Korisnik[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status
        },
        error=>{
          this.status = !this.status
        }
      )
    else if(this.svrha === "Prodavac")
      this.korisnikService.filterByPageProdavac(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Korisnik[];
          this.totalSize = Number(res.body.totalElements);
          this.ukljucioFilter = true;
          this.status = !this.status
        },
        error=>{
          this.status = !this.status
        }
      )
    else if(this.svrha === "Admin")
      this.korisnikService.filterByPageAdmin(this.filter,this.currentPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Korisnik[];
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
        this.korisnikService.filterByPageMusterija(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Korisnik[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.korisnikService.getByPageMusterija(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Korisnik[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
    else if(this.svrha === "Prodavac")
      if (this.ukljucioFilter){
        this.korisnikService.filterByPageProdavac(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Korisnik[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.korisnikService.getByPageProdavac(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Korisnik[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
    else if(this.svrha === "Admin")
      if (this.ukljucioFilter){
        this.korisnikService.filterByPageAdmin(this.filter,newPage-1,this.pageSize).subscribe(
          res =>{
            this.lista = res.body.content as Korisnik[];
            this.totalSize = Number(res.body.totalElements);
          }
        )
      }else {
        this.korisnikService.getByPageAdmin(newPage - 1,this.pageSize).subscribe(
          res=>{
            this.lista = res.content as Korisnik[];
            this.totalSize = Number(res.totalElements);
          }
        )
      }
  }
}
