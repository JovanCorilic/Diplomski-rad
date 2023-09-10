import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { Korisnik } from 'src/app/MODEL/Korisnik';
import { KorisnikService } from 'src/app/SERVICE/Korisnik.service';

@Component({
  selector: 'app-tabela-korisnik',
  templateUrl: './tabela-korisnik.component.html',
  styleUrls: ['./tabela-korisnik.component.css']
})
export class TabelaKorisnikComponent implements OnInit,OnChanges{
  @Input() listaKorisnika:Korisnik[]|undefined;

  displayedColumns: string[] = ['email', 'ime', 'prezime', 'povlacenje-akaunta'];
  dataSource:Korisnik[] = []

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private korisnikService:KorisnikService,
    private _snackBar: MatSnackBar
  ){

  }

  ngOnInit(): void {
    this.dataSource = []
    if (this.listaKorisnika !==undefined){
      this.dataSource = this.listaKorisnika
    }
  }

  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaKorisnika.currentValue !== undefined){
      this.dataSource = changes.listaKorisnika.currentValue
    }
  }

  povuci(email:string){
    this.korisnikService.povuciKorisnika(email).subscribe(
      res=>{
        this.openSnackBar("Uspešno povučen korisnik sa mejlom : "+email)
      },
      error=>{

      }
    )
  }

  vrati(email:string){
    this.korisnikService.vratiKorisnika(email).subscribe(
      res=>{
        this.openSnackBar("Uspešno vraćen korisnik sa mejlom : "+email)
      }
    )
  }

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
  
}
