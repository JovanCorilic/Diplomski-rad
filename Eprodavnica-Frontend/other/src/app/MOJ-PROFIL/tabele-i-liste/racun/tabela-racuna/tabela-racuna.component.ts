import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Racun } from 'src/app/MODEL/Racun';

@Component({
  selector: 'app-tabela-racuna',
  templateUrl: './tabela-racuna.component.html',
  styleUrls: ['./tabela-racuna.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class TabelaRacunaComponent {
  @Input() listaRacuna:Racun[]|undefined;

  dataSource:Racun[] = []
  columnsToDisplay = ['konacnaCena', 'datumKreiranja'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement = <Racun>{}

  constructor(
    private router:Router,
  ){
    if (this.listaRacuna !== undefined)
      this.dataSource = this.listaRacuna
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }

  idiNaIndividualniRacun(brojRacuna:string){
    this.router.navigate(['/other/racun/'+brojRacuna])
  }
}
