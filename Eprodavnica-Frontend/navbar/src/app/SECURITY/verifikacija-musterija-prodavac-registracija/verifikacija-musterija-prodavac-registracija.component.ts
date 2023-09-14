import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/SERVICE/authentication.service';

@Component({
  selector: 'app-verifikacija-musterija-prodavac-registracija',
  templateUrl: './verifikacija-musterija-prodavac-registracija.component.html',
  styleUrls: ['./verifikacija-musterija-prodavac-registracija.component.css']
})
export class VerifikacijaMusterijaProdavacRegistracijaComponent {
  token = <string>{};
  status: boolean = true;

  constructor(
    private authenticationService:AuthenticationService,
    private router:Router,
    private route:ActivatedRoute,
  ){
    let temp=this.route.snapshot.paramMap.get('token');
        if(temp != null)
            this.token = temp;
        else
          this.token = "nista";
  }

  potvrdiZahtev(){
    this.status = !this.status; 
    this.authenticationService.verifikacijaRegistracije(this.token).subscribe(
      res=>{
        this.router.navigate(['']);
      }
    )
  }
}
