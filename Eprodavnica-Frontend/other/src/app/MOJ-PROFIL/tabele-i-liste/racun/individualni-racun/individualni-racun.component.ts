import { Component, OnInit } from '@angular/core';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Artikal } from 'src/app/MODEL/Artikal';
import { Racun } from 'src/app/MODEL/Racun';
import { RacunService } from 'src/app/SERVICE/Racun.service';

@Component({
  selector: 'app-individualni-racun',
  templateUrl: './individualni-racun.component.html',
  styleUrls: ['./individualni-racun.component.css']
})
export class IndividualniRacunComponent implements OnInit{
  lista:Artikal[]|undefined;
  brojRacuna=<string>{}
  racun = <Racun>{}
  pageSize: number;
  currentPage: number;
  totalSize: number;

  status:boolean = false

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private racunService:RacunService,
    private route:ActivatedRoute,
    private _snackBar: MatSnackBar
  ){
    this.pageSize = 6;
		this.currentPage = 1;
		this.totalSize = 1;

    let temp=this.route.snapshot.paramMap.get('brojRacuna');
    if(temp != null)
        this.brojRacuna = temp;
    else
      this.brojRacuna = "nista";
  }

  ngOnInit(): void {
    this.racunService.dajRacun(this.brojRacuna).subscribe(
      res=>{
        this.racun = res
      }
    )

    this.racunService.getByPageArtikal(this.currentPage - 1,this.pageSize,this.brojRacuna).subscribe(
      res=>{
        this.lista = res.content as Artikal[];
        this.totalSize = Number(res.totalElements);
      }
    )
  }

  changePage(newPage: number) {
    this.racunService.getByPageArtikal(newPage - 1,this.pageSize,this.brojRacuna).subscribe(
      res=>{
        this.lista = res.content as Artikal[];
        this.totalSize = Number(res.totalElements);
      }
    )
  }

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  plati(){
    this.status = !this.status
    this.racunService.plati(this.brojRacuna).subscribe(
      res=>{
        this.status = !this.status
        this.openSnackBar("Uspešno plaćeno!")
        this.racun.korpa = false;
      },
      error=>{
        this.status = !this.status
      }
    )
  }

  smanjiCenu(cena:number){
    this.racun.konacnaCena = this.racun.konacnaCena - cena;
  }

}
