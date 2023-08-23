import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { ProduktMini } from './MODEL/ProduktMini';
import { ProduktMiniService } from './SERVICE/ProduktMini.service';
import { Tip } from './MODEL/Tip';
import { TipService } from './SERVICE/Tip.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

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
    this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;

    this.filterForm = fBuilder.group({
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

  filter(){
    
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  get tipovi() : FormArray {
    let temp = this.tipoviForm.value.skills.at(0);
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

  changePage(newPage: number) {
    this.produktMiniService.getByPage(newPage - 1,this.pageSize).subscribe(
      res=>{
        this.lista = res.content as ProduktMini[];
        this.totalSize = Number(res.totalElements);
      }
    )
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
