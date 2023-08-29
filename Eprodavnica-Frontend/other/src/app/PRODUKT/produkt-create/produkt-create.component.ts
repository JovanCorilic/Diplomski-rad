import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Produkt } from 'src/app/MODEL/Produkt';
import { Tip } from 'src/app/MODEL/Tip';
import { ProduktService } from 'src/app/SERVICE/Produkt.service';
import { TipService } from 'src/app/SERVICE/Tip.service';

@Component({
  selector: 'app-produkt-create',
  templateUrl: './produkt-create.component.html',
  styleUrls: ['./produkt-create.component.css']
})
export class ProduktCreateComponent implements OnInit{
  produktForm: FormGroup
  produkt = <Produkt>{}
  listaTipova: Tip[] = [];

  constructor(
    private router:Router,
    private produktService:ProduktService,
    private fBuilder: FormBuilder,
    private modalService: NgbModal,
    private tipService:TipService
  ){
    this.produktForm = fBuilder.group({
      naziv: ["",[Validators.required]],
      deskripcija: ["",[Validators.required]],
      cena: [-1,[Validators.required,this.notANumber()]],
      tipovi: this.fBuilder.array([])
    })
  }

  ngOnInit(): void {
    this.tipService.getAllTip().subscribe(
      res=>{
        this.listaTipova = res;
        for( let i in this.listaTipova ){
          this.addTip();
        }
      }
    )
  }

  create(){
    this.produktService.napraviProdukt(this.produkt).subscribe(
      res=>{

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
    else
    return temp.hasError('email') ? 'To nije validan email' : '';
  }

  get tipovi() : FormArray {
    return this.produktForm.get("tipovi") as FormArray
  }

  newTip(): FormGroup {
    return this.fBuilder.group({
      tip:false
    })
  }

  addTip() {
    this.tipovi.push(this.newTip());
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
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
