import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Korisnik } from 'src/app/MODEL/Korisnik';
import { KorisnikService } from 'src/app/SERVICE/Korisnik.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit{
  admin = <Korisnik>{}
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
    this.korisnikService.dajAdmin().subscribe(
      res=>{
        this.admin = res;
        this.korisnikForm.controls.ime.setValue(this.admin.ime);
        this.korisnikForm.controls.prezime.setValue(this.admin.prezime);
      }
    )
  }

  update(){
    this.status = !this.status
    this.admin.ime = this.korisnikForm.value.ime;
    this.admin.prezime = this.korisnikForm.value.prezime;
    this.korisnikService.updateKorisnik(this.admin).subscribe(
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
}
