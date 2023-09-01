import { Component, OnInit } from '@angular/core';
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

  constructor(
    private racunService:RacunService,
    private route:ActivatedRoute,
  ){
    this.pageSize = 2;
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

}
