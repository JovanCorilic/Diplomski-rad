import { Component, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/SERVICE/authentication.service';

@Component({
  selector: 'app-promena-lozinke',
  templateUrl: './promena-lozinke.component.html',
  styleUrls: ['./promena-lozinke.component.css']
})
export class PromenaLozinkeComponent {
  token = <string>{};
  status: boolean = false;

  constructor(
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog,
  ){
    let temp=this.route.snapshot.paramMap.get('token');
        if(temp != null)
            this.token = temp;
        else
          this.token = "nista";
  }

  ngOnInit(): void {
    setTimeout(() => 
    {
      const dialogRef = this.dialog.open(PromenaLozinkeDialog, {
        data: this.token,
      });

      dialogRef.afterClosed().subscribe(result => {
        this.router.navigate(['']);
      });
    },
    100);
  }
}

@Component({
  selector: 'promena-lozinke-dialog',
  templateUrl: 'promena-lozinke-dialog.html'
})
export class PromenaLozinkeDialog {
  status: boolean = false;
  promenaSifreForm:FormGroup

  constructor(
    private authenticationService:AuthenticationService,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public token: string,
    private fBuilder: FormBuilder,
  ){

    this.promenaSifreForm = this.fBuilder.group({
      lozinka : ["",[Validators.required,this.daLiSuLozinkeJednake2()]],
      lozinkaPonovo : ["",[Validators.required, this.daLiSuLozinkeJednake()]]
    })

  }

  promeniSifru(){
    console.log('Ovde')
    this.status = !this.status; 
    let lozinka = this.promenaSifreForm.value.lozinka;
    this.authenticationService.promeniLozinku(this.token,lozinka).subscribe(
      res=>{
        this.status = !this.status; 
      }
    )
  }

  daLiSuLozinkeJednake(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value

      const jednake = (str: string): boolean => {
        if (this.promenaSifreForm !== undefined){
          if (str === this.promenaSifreForm.value.lozinka){
            this.promenaSifreForm.controls.lozinka.setErrors(null)
            return true;
          }else{
            return false;
          }
        }
        else
          return true;
      }

      return (!jednake(nV) && !control.pristine) ? {daLiSuLozinkeJednake: true} : null;
    };
  }
//formData.form.controls['email'].setErrors({'incorrect': true});
  daLiSuLozinkeJednake2(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      
      const jednake = (str: string): boolean => {
        if (this.promenaSifreForm !== undefined){
          if (str === this.promenaSifreForm.value.lozinkaPonovo){
            this.promenaSifreForm.controls.lozinkaPonovo.setErrors(null)
            return true;
          }else{
            return false;
          }
        }
        else
          return true;
      }

      return (!jednake(nV) && !control.pristine) ? {daLiSuLozinkeJednake2: true} : null;
    };
  }

  getErrorMessage(temp:any) {
    if (temp.hasError('required')) {
      return 'Morate uneti vrednost';
    }
    else if(temp.hasError('daLiSuLozinkeJednake2')){
      return 'Nisu iste lozinke';
    }
    else
      return temp.hasError('daLiSuLozinkeJednake') ? 'Nisu iste lozinke' : '';
  }

}
