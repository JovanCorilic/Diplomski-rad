import { Component, ElementRef, Inject, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/SERVICE/authentication.service';

@Component({
  selector: 'app-verifikacija-musterija-prodavac-registracija',
  templateUrl: './verifikacija-musterija-prodavac-registracija.component.html',
  styleUrls: ['./verifikacija-musterija-prodavac-registracija.component.css']
})
export class VerifikacijaMusterijaProdavacRegistracijaComponent implements OnInit{
  token = <string>{};

  constructor(
    public dialog: MatDialog,
    private route:ActivatedRoute,
    private router:Router,
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
      const dialogRef = this.dialog.open(VerifikacijaMusterijaProdavacRegistracijaComponentDialog, {
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
  selector: 'dialog-elements-example-dialog-verifikacija-musterija-prodavac',
  templateUrl: 'dialog-elements-example-dialog-verifikacija-musterija-prodavac.html'
})
export class VerifikacijaMusterijaProdavacRegistracijaComponentDialog {
  status: boolean = false;

  constructor(
    private authenticationService:AuthenticationService,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public token: string,
  ){

  }

  potvrdiZahtev(){
    this.status = !this.status; 
    this.authenticationService.verifikacijaRegistracije(this.token).subscribe(
      res=>{
        this.status = !this.status; 
      }
    )
  }
}
