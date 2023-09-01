import { Component, Input } from '@angular/core';
import { ProduktMini } from '../MODEL/ProduktMini';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tabela-dugmadi',
  templateUrl: './tabela-dugmadi.component.html',
  styleUrls: ['./tabela-dugmadi.component.css']
})
export class TabelaDugmadiComponent {
  @Input() listaProdukataMini:ProduktMini[]|undefined;

  constructor(
    private router:Router,
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }

  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['/other/produktDetaljno/'+serijskiBroj]);
  }

}
