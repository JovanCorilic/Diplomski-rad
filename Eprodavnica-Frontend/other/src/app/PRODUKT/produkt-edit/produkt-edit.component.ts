import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TipFilter } from 'src/app/MODEL/Filter/TipFilter';
import { Produkt } from 'src/app/MODEL/Produkt';
import { Tip } from 'src/app/MODEL/Tip';
import { ProduktService } from 'src/app/SERVICE/Produkt.service';
import { TipService } from 'src/app/SERVICE/Tip.service';

@Component({
  selector: 'app-produkt-edit',
  templateUrl: './produkt-edit.component.html',
  styleUrls: ['./produkt-edit.component.css']
})
export class ProduktEditComponent implements OnInit{
  produkt = <Produkt>{}
  serijskiBroj=<string>{}
  produktForm: FormGroup
  listaTipova: Tip[] = [];
  status:boolean= false

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private produktService:ProduktService,
    private route:ActivatedRoute,
    private modalService: NgbModal,
    private fBuilder: FormBuilder,
    private tipService:TipService,
    private _snackBar: MatSnackBar
  ){
    let temp=this.route.snapshot.paramMap.get('serijskiBroj');
    if(temp != null)
        this.serijskiBroj = temp;
    else
      this.serijskiBroj = "nista";

    this.produktForm = fBuilder.group({
      naziv: ["",[Validators.required]],
      deskripcija: ["",[Validators.required]],
      cena: [-1,[Validators.required,this.notANumber(),this.viseOdNula()]],
      tipovi: this.fBuilder.array([])
    })
  }

  ngOnInit(): void {
    this.produktService.dajProdukt(this.serijskiBroj).subscribe(
      res=>{
        this.produkt = res;

        this.produktForm.controls.naziv.setValue(this.produkt.naziv);
        this.produktForm.controls.deskripcija.setValue(this.produkt.deskripcija);
        this.produktForm.controls.cena.setValue(this.produkt.cena);

        this.tipService.getAllTipNormalno().subscribe(
          res=>{
            this.listaTipova = res;
            for( let tip of this.listaTipova ){
              if(this.includes(this.produkt.listaTipova,tip)){
                this.addTip(true);
              }else{
                this.addTip(false);
              }
            }
          }
        )
      }
    )
  }

  includes(lista:Tip[],tip:Tip):boolean{
    for( let temp of lista ){
      if (temp.naziv === tip.naziv)
        return true;
    }
    return false;
  }

  update(){
    this.status = !this.status
    this.produkt.naziv = this.produktForm.value.naziv;
    this.produkt.deskripcija = this.produktForm.value.deskripcija;
    this.produkt.cena = this.produktForm.value.cena;

    this.produkt.listaTipova = []
    for( let i in this.listaTipova ){
      if (this.produktForm.value.tipovi.at(i).tip){
        this.produkt.listaTipova.push(new Tip(this.listaTipova[i].naziv,0));
      }
    }

    this.produktService.updateProdukt(this.produkt,this.serijskiBroj).subscribe(
      res=>{
        this.status = !this.status
        this.openSnackBar("Uspešno promenjene vrednosti produkta!")
      },
      error=>{
        this.status = !this.status
      }
    )
  }

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  get tipovi() : FormArray {
    return this.produktForm.get("tipovi") as FormArray
  }

  newTip(temp:boolean): FormGroup {
    return this.fBuilder.group({
      tip:temp
    })
  }

  addTip(temp:boolean) {
    this.tipovi.push(this.newTip(temp));
  }

  getErrorMessage(temp:any) {
    if (temp.hasError('required')) {
      return 'Morate uneti vrednost';
    }
    else if (temp.hasError('notANumber')) {
      return 'Morate uneti broj';
    }
    else
    return temp.hasError('viseOdNula') ? 'Mora biti više od 0' : '';
  }

  viseOdNula():ValidatorFn{
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      if (typeof value == 'string') {
        nV = value.replace(',', '.')
      }
      return (Number.isNaN(Number(nV)) && !control.pristine && (Number(nV)>0)) ? {viseOdNula: true} : null;
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
