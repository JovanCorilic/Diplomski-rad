import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Musterija } from 'src/app/MODEL/Musterija';
import { KorisnikService } from 'src/app/SERVICE/Korisnik.service';


@Component({
  selector: 'app-musterija',
  templateUrl: './musterija.component.html',
  styleUrls: ['./musterija.component.css']
})
export class MusterijaComponent implements OnInit{
  musterija = <Musterija>{}
  status:boolean = false

  korisnikForm : FormGroup

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private korisnikService:KorisnikService,
    private _snackBar: MatSnackBar,
    private modalService: NgbModal,
    private fBuilder: FormBuilder,
  ){

    this.korisnikForm = this.fBuilder.group({
      ime: ["",[Validators.required]],
      prezime: ["",[Validators.required]],
    })

  }

  ngOnInit(): void {
    this.korisnikService.dajMusteriju().subscribe(
      res=>{
        this.musterija = res;
        this.korisnikForm.controls.ime.setValue(this.musterija.ime);
        this.korisnikForm.controls.prezime.setValue(this.musterija.prezime);
      }
    )
  }

  update(){
    this.status = !this.status
    this.musterija.ime = this.korisnikForm.value.ime;
    this.musterija.prezime = this.korisnikForm.value.prezime;
    this.korisnikService.updateKorisnik(this.musterija).subscribe(
      res=>{
        this.status = !this.status
        this.openSnackBar("Uspešno promenjene lične informacije!")
      },
      error =>{
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

  getErrorMessage(temp:any) {
    if (temp.hasError('required')) {
      return 'Morate uneti vrednost';
    }
    else if (temp.hasError('notANumber')) {
      return 'Morate uneti broj';
    }
    else if (temp.hasError('daLiImaSpecijalanKarakter')) {
      return 'Morate uneti specijalni karakter';
    }
    else
    return temp.hasError('email') ? 'To nije validan email' : '';
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
