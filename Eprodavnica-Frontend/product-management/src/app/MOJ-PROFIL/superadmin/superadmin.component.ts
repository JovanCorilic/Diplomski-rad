import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl, ValidatorFn } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { PravljenjeAdmina } from 'src/app/MODEL/PravljanjeAdmina';
import { KorisnikService } from 'src/app/SERVICE/Korisnik.service';

@Component({
  selector: 'app-superadmin',
  templateUrl: './superadmin.component.html',
  styleUrls: ['./superadmin.component.css']
})
export class SuperadminComponent {
  admin = <PravljenjeAdmina>{}
  status:boolean = false

  korisnikForm : FormGroup

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private korisnikService:KorisnikService,
    private _snackBar: MatSnackBar,
    private fBuilder: FormBuilder,
  ){
    this.korisnikForm = this.fBuilder.group({
      ime: ["",[Validators.required]],
      prezime: ["",[Validators.required]],
      email: ["",[Validators.required,Validators.email]],
      lozinka: ["",[Validators.required, Validators.minLength(5),this.daLiImaBroj(),this.daLiImaSpecijalanKarakter()]]
    })
  }

  createAdmin(){
    this.status = !this.status
    this.admin.ime = this.korisnikForm.value.ime;
    this.admin.prezime = this.korisnikForm.value.prezime;
    this.admin.email = this.korisnikForm.value.email;
    this.admin.lozinka = this.korisnikForm.value.lozinka;
    this.korisnikService.createAdmin(this.admin).subscribe(
      res=>{
        this.status = !this.status
        this.openSnackBar('Uspešno napravljen admin.')
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
    else if( temp.hasError('minLength')){
      return 'Mora biti dužine barem 5 karaktera'
    }
    else
      return temp.hasError('email') ? 'To nije validan email' : '';
  }

  daLiImaBroj(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      if (typeof value == 'string') {
        nV = value.replace(',', '.')
      }
      const hasNumbers = (str: string): boolean => {
        const regex = /\d/;
        return regex.test(str);
      }

      return (!hasNumbers(nV) && !control.pristine) ? {daLiImaBroj: true} : null;
    };
  }

  daLiImaSpecijalanKarakter(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      if (typeof value == 'string') {
        nV = value.replace(',', '.')
      }
      const hasSpecialChars = (str: string): boolean => {
        const regex = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
        return regex.test(str);
      }

      return (!hasSpecialChars(nV) && !control.pristine) ? {daLiImaSpecijalanKarakter: true} : null;
    };
  }
}
