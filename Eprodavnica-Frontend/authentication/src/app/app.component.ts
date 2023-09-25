import { AuthenticationService } from './SERVICE/authentication.service';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Musterija } from './MODEL/Musterija';
import { JwtHelperService } from '@auth0/angular-jwt';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { Racun } from './MODEL/Racun';

@Component({
  selector: 'navbar-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = 'navbar';
  registerForm: FormGroup;
  logForm: FormGroup;
  failLogin:number=0;
  musterija = <Musterija>{};
  status: boolean = false;
  status2: boolean = false;
  status3:boolean = false;
  racun=<Racun>{};

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    public router:Router,
    private authenticationService:AuthenticationService,
    private fBuilder: FormBuilder,
    private modalService: NgbModal,
    private _snackBar: MatSnackBar
  ){
    this.registerForm = this.fBuilder.group({
      ime: ["",[Validators.required]],
      prezime: ["",[Validators.required]],
      email: ["",[Validators.required,Validators.email]],
      lozinka: ["",[Validators.required, Validators.minLength(5),this.daLiImaBroj(),this.daLiImaSpecijalanKarakter()]],
      uloga: "ROLE_MUSTERIJA"
    });

    this.logForm = this.fBuilder.group({
      email: ["",[Validators.required, Validators.email]],
      password: ["",[Validators.required]]
    });
  }

  idiNaProfil(uloga:string){
    if (uloga === "ROLE_MUSTERIJA"){
      this.router.navigate(['/other/musterija']);
    }else if(uloga === "ROLE_PRODAVAC"){
      this.router.navigate(['/other/prodavac']);
    }
  }

  idiNaPregledSajtaSuperadmin(){
    this.router.navigate(['/user-management/superadmin']);
  }

  pravljenjeProdukta(){
    this.router.navigate(['/other/pravljenjeProdukta']);
  }

  pregledSajta(){
    this.router.navigate(['/other/admin']);
  }

  logOut() {
    this.status3 = !this.status3
    this.authenticationService.logout().subscribe(
			result => {
        this.status3 = !this.status3
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('uloga');
        this.router.navigate(['']);
			},
      error =>{
        this.status3 = !this.status3
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('uloga');
        this.router.navigate(['']);
      }
		);
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

  logIn(){
    this.status= !this.status;
		const auth: any = {};
		auth.username = this.logForm.value.email;
		auth.password = this.logForm.value.password;
		this.authenticationService.login(auth).subscribe(
			result => {
        sessionStorage.setItem('user', JSON.stringify(result));
        const item = sessionStorage.getItem('user');
		    const decodedItem = JSON.parse(item!);
        sessionStorage.setItem('accessToken', decodedItem.accessToken);
        const jwt: JwtHelperService = new JwtHelperService();
        const info = jwt.decodeToken(decodedItem.accessToken);
        //sessionStorage.setItem('uloga', info['uloga']);

        this.status= !this.status;
        this.openSnackBar("Uspešno ulogovan");

        
			},
			error => {
        this.status= !this.status;
        alert("Loš login!");
        this.failLogin += 1;
			}
		);
	}

  idiNaKorpu(){
    this.authenticationService.dajAktivanRacun().subscribe(
      res=>{
        this.racun = res;
        if (this.racun.brojRacuna==='nema'){
          this.openSnackBar("Korpa je prazna")
        }else if (this.racun.brojRacuna === undefined){
          this.openSnackBar("Korpa je prazna")
        }else
          this.router.navigate(['/bill-management/racun/'+this.racun.brojRacuna])
      }
    )
    
  }

  getRole():string{
    const item = sessionStorage.getItem('user');

    if(!item){
      return "";
    }

    const jwt:JwtHelperService = new JwtHelperService();
    const decodedItem = JSON.parse(item!);
    const info = jwt.decodeToken(decodedItem.accessToken);
    return info['uloga'];
  }

  register(){
    this.status2= !this.status2;
    this.musterija.email = this.registerForm.value.email;
    this.musterija.ime = this.registerForm.value.ime;
    this.musterija.prezime = this.registerForm.value.prezime;
    this.musterija.lozinka = this.registerForm.value.lozinka;
    this.musterija.uloga = this.registerForm.value.uloga;
    this.authenticationService.register(this.musterija).subscribe(
      res=>{
        this.status2= !this.status2;
        this.openSnackBar("Uspešno napravljen zahtev za registraciju, molimo vas potvrdite preko email da bi ste nastavili dalje");
      },error =>{
        alert("Nisu pravilno uneti podaci!")
        this.status2 = !this.status2;
      }
    )
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  goToHome() {
    this.router.navigate(['/']);
  }

  goToLogin(){
    
    this.router.navigate(['/home']);
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
