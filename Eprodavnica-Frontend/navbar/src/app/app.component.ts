import { AuthenticationService } from './SERVICE/authentication.service';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Musterija } from './MODEL/Musterija';
import { JwtHelperService } from '@auth0/angular-jwt';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'navbar-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'navbar';
  registerForm: FormGroup;
  logForm: FormGroup;
  failLogin:number=0;
  musterija = <Musterija>{};
  status: boolean = false;
  status2: boolean = false;

  constructor(
    private router:Router,
    private authenticationService:AuthenticationService,
    private fBuilder: FormBuilder,
    private modalService: NgbModal
  ){
    this.registerForm = this.fBuilder.group({
      ime: ["",[Validators.required]],
      prezime: ["",[Validators.required]],
      email: ["",[Validators.required,Validators.email]],
      lozinka: ["",[Validators.required, Validators.minLength(4),this.daLiImaBroj(),this.daLiImaSpecijalanKarakter()]]
    });

    this.logForm = this.fBuilder.group({
      email: ["",[Validators.required, Validators.email]],
      password: ["",[Validators.required]]
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
        sessionStorage.setItem('uloga', info['uloga']);
        this.status= !this.status;
			},
			error => {
        this.status= !this.status;
        alert("Loš login!");
        this.failLogin += 1;
			}
		);
	}

  register(){
    this.status2= !this.status2;
    this.musterija.email = this.registerForm.value.email;
    this.musterija.ime = this.registerForm.value.ime;
    this.musterija.prezime = this.registerForm.value.prezime;
    this.musterija.lozinka = this.registerForm.value.lozinka;
    this.authenticationService.register(this.musterija).subscribe(
      res=>{
        this.status2= !this.status2;
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