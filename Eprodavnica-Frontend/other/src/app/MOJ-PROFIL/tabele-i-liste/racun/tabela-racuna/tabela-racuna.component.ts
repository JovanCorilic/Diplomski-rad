import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
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
export class TabelaRacunaComponent implements OnInit, OnChanges{
  @Input() listaRacuna:Racun[]|undefined;

  dataSource:Racun[] = []
  columnsToDisplay = ['konacnaCena', 'datumKreiranja'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement = <Racun>{}

  constructor(
    private router:Router,
  ){
    
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }
  ngOnInit(): void {
    this.dataSource = []
    if (this.listaRacuna !== undefined)
      this.dataSource = this.listaRacuna
  }
  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaRacuna.currentValue !==undefined)
      this.dataSource = changes.listaRacuna.currentValue
  }

  idiNaIndividualniRacun(brojRacuna:string){
    this.router.navigate(['racun/'+brojRacuna])
  }
}
