import { Produkt } from './../../MODEL/Produkt';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Filter } from 'src/app/MODEL/Filter/Filter';
import { Ocena } from 'src/app/MODEL/Filter/Ocena';
import { Recenzija } from 'src/app/MODEL/Recenzija';
import { ProduktService } from 'src/app/SERVICE/Produkt.service';
import { RecenzijaService } from 'src/app/SERVICE/Recenzija.service';


@Component({
  selector: 'app-produkt-detaljno',
  templateUrl: './produkt-detaljno.component.html',
  styleUrls: ['./produkt-detaljno.component.css']
})
export class ProduktDetaljnoComponent implements OnInit {
  produkt = <Produkt>{}
  serijskiBroj=<string>{}
  filter = <Filter>{}
  lista:Recenzija[]|undefined;
  ukljucioFilter:boolean = false;

  listaOcena: number[] = [1,2,3,4,5]
  pageSize: number;
  currentPage: number;
  totalSize: number;
  ocenaForm : FormGroup;
  
  constructor(
    private produktService:ProduktService,
    private recenzijaService:RecenzijaService,
    private route:ActivatedRoute,
    private modalService: NgbModal,
    private fBuilder: FormBuilder,
  ){
    this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;

    let temp=this.route.snapshot.paramMap.get('id');
    if(temp != null)
        this.serijskiBroj = temp;
    else
      this.serijskiBroj = "nista";

    this.ocenaForm = fBuilder.group({
      ocene: this.fBuilder.array([])
    })
  }
  
  ngOnInit(): void {
    this.produktService.dajProdukt(this.serijskiBroj).subscribe(
      res=>{
        this.produkt = res;
      }
    )

    this.recenzijaService.getByPage(this.currentPage - 1,this.pageSize,this.serijskiBroj).subscribe(
      res =>{
        this.lista = res.content as Recenzija[];
        this.totalSize = Number(res.totalElements);
      }
    )

    for (let i in this.listaOcena){
      this.addOcena();
    }
  }

  

  resetuj(){
    this.filter = <Filter>{}
    this.ocenaForm.reset();
    this.ukljucioFilter = false;
    this.recenzijaService.getByPage(this.currentPage - 1,this.pageSize,this.serijskiBroj).subscribe(
      res =>{
        this.lista = res.content as Recenzija[];
        this.totalSize = Number(res.totalElements);
      }
    )
  }

  filters(){
    this.filter.ocena = []
    for( let i in this.listaOcena ){
      this.filter.ocena.push(new Ocena(this.listaOcena[i],this.ocenaForm.value.ocene.at(i).ocena));
    }
    this.recenzijaService.filterByPage(this.filter,this.currentPage-1,this.pageSize,this.serijskiBroj).subscribe(
      res =>{
        this.lista = res.body.content as Recenzija[];
        this.totalSize = Number(res.body.totalElements);
        this.ukljucioFilter = true;
      }
    )
  }

  changePage(newPage: number) {
    if (this.ukljucioFilter){
      this.recenzijaService.filterByPage(this.filter,newPage-1,this.pageSize,this.serijskiBroj).subscribe(
        res =>{
          this.lista = res.body.content as Recenzija[];
          this.totalSize = Number(res.body.totalElements);
        }
      )
    }else {
      this.recenzijaService.getByPage(newPage - 1,this.pageSize,this.serijskiBroj).subscribe(
        res=>{
          this.lista = res.content as Recenzija[];
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

  get ocene() : FormArray {
    return this.ocenaForm.get("ocene") as FormArray
  }

  newOcena(): FormGroup {
    return this.fBuilder.group({
      ocena:false
    })
  }

  addOcena(){
    this.ocene.push(this.newOcena());
  }

}
