import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Tip } from 'src/app/MODEL/Tip';
import { TipService } from 'src/app/SERVICE/Tip.service';

@Component({
  selector: 'app-tabela-tip',
  templateUrl: './tabela-tip.component.html',
  styleUrls: ['./tabela-tip.component.css']
})
export class TabelaTipComponent implements OnInit,OnChanges{
  @Input() listaTipova:Tip[]|undefined;

  displayedColumns: string[] = ['naziv', 'edit'];
  dataSource:Tip[] = []

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  tipForm:FormGroup;

  naziv:string="";

  tip = <Tip>{}

  constructor(
    private tipService:TipService,
    private _snackBar: MatSnackBar,
    private modalService: NgbModal,
    private fBuilder: FormBuilder,
  ){
    this.tipForm = fBuilder.group({
      naziv: ["",[Validators.required]],
    });
  }

  ngOnInit(): void {
    this.dataSource = []
    if (this.listaTipova !==undefined){
      this.dataSource = this.listaTipova
    }
  }

  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaTipova.currentValue !== undefined){
      this.dataSource = changes.listaTipova.currentValue
    }
  }

  edit(){
    this.tip.naziv = this.tipForm.value.naziv
    this.tipService.updateTip(this.tip,this.naziv).subscribe(
      res=>{
        this.openSnackBar("Uspešno promenjen naziv tipa")
        this.naziv = this.tip.naziv;
      },
      error=>{
        this.openSnackBar("Tip sa tim nazivom već postoji")
      }
    )
  }

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  open(content:any,naziv:string) {
    this.tipForm.controls.naziv.setValue(naziv);
    this.naziv = naziv
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  getErrorMessage(temp:any) {
    return temp.hasError('required') ? 'Morate uneti vrednost' : '';
    }
    
  
}
