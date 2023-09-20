import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/SERVICE/authentication.service';

@Component({
  selector: 'app-verifikacija-admin-registracija',
  templateUrl: './verifikacija-admin-registracija.component.html',
  styleUrls: ['./verifikacija-admin-registracija.component.css']
})
export class VerifikacijaAdminRegistracijaComponent implements OnInit{
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
      const dialogRef = this.dialog.open(VerifikacijaAdminRegistracijaComponentDialog, {
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
  selector: 'app-verifikacija-admin-registracija-dialog',
  templateUrl: 'app-verifikacija-admin-registracija-dialog.html'
})
export class VerifikacijaAdminRegistracijaComponentDialog {
  status: boolean = false;

  constructor(
    private authenticationService:AuthenticationService,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public token: string,
  ){

  }

  potvrdiZahtev(){
    this.status = !this.status; 
    this.authenticationService.verifikacijaAdminNalog(this.token).subscribe(
      res=>{
        this.status = !this.status; 
      }
    )
  }

}
