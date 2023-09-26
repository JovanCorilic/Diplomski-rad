import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Filter } from 'src/app/MODEL/Filter/Filter';
import { Tip } from 'src/app/MODEL/Tip';
import { TipService } from 'src/app/SERVICE/Tip.service';

@Component({
  selector: 'app-lista-tip-page',
  templateUrl: './lista-tip-page.component.html',
  styleUrls: ['./lista-tip-page.component.css']
})
export class ListaTipPageComponent implements OnInit{
  pageSize: number;
  currentPage: number;
  totalSize: number;

  filter = <Filter>{}
  ukljucioFilter:boolean = false;
  status:boolean=false;
  status2:boolean=false;
  status3:boolean=false;

  lista:Tip[]|undefined;

  tip = <Tip>{}

  filterForm : FormGroup;
  tipForm:FormGroup;

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private fBuilder: FormBuilder,
    private tipService:TipService,
    private modalService: NgbModal,
    private _snackBar: MatSnackBar
  ){
    this.pageSize = 12;
		this.currentPage = 1;
		this.totalSize = 1;

    this.filterForm = fBuilder.group({
      naziv: ["",[Validators.required]],
    });

    this.tipForm = fBuilder.group({
      naziv: ["",[Validators.required]],
    });
  }

  ngOnInit(): void {
    this.tipService.getByPage(this.currentPage - 1,this.pageSize).subscribe(
      res =>{
        this.lista = res.content as Tip[];
        this.totalSize = Number(res.totalElements);
      }
    )
  }

  napravi(){
    this.tip.naziv = this.tipForm.value.naziv
    this.tipService.createTip(this.tip).subscribe(
      res=>{
        this.openSnackBar("Uspešno napravljen tip")
      },
      error=>{
        this.openSnackBar("Tip sa tim nazivom već postoji")
      }
    )
  }

  resetuj(){
    this.status2 = !this.status2
    this.filter = <Filter>{}
    this.filterForm.reset();

    this.ukljucioFilter = false;
    this.tipService.getByPage(this.currentPage - 1,this.pageSize).subscribe(
      res =>{
        this.lista = res.content as Tip[];
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

    this.tipService.filterByPage(this.filter,this.currentPage-1,this.pageSize).subscribe(
      res =>{
        this.lista = res.body.content as Tip[];
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
    if (this.ukljucioFilter){
      this.tipService.filterByPage(this.filter,newPage-1,this.pageSize).subscribe(
        res =>{
          this.lista = res.body.content as Tip[];
          this.totalSize = Number(res.body.totalElements);
        }
      )
    }else {
      this.tipService.getByPage(newPage - 1,this.pageSize).subscribe(
        res=>{
          this.lista = res.content as Tip[];
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

  newTip(): FormGroup {
    return this.fBuilder.group({
      tip:false
    })
  }
 
  addTip() {
    this.tipovi.push(this.newTip());
  }

  getErrorMessage(temp:any) {
    if (temp.hasError('required')) {
      return 'Morate uneti vrednost';
    }
    else if (temp.hasError('daLiImaBroj')) {
      return 'Morate uneti broj';
    }
    else if (temp.hasError('daLiImaSpecijalanKarakter')) {
      return 'Morate uneti specijalni karakter';
    }
    else if( temp.hasError('minLength')){
      return 'Mora biti dužine barem 5 karaktera'
    }
    else
    return temp.hasError('email') ? 'To nije validan email' : '';
  }

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}
