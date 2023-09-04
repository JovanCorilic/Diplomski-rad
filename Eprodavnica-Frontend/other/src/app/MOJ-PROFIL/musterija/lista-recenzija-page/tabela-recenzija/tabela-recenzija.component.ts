import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { Recenzija } from 'src/app/MODEL/Recenzija';

@Component({
  selector: 'app-tabela-recenzija',
  templateUrl: './tabela-recenzija.component.html',
  styleUrls: ['./tabela-recenzija.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class TabelaRecenzijaComponent implements OnInit, OnChanges{
  @Input() listaRecenzija:Recenzija[]|undefined;

  dataSource:Recenzija[] = []
  columnsToDisplay = ['ocena', 'datumPravljenja', 'serijskiBrojProdukt'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement = <Recenzija>{}

  constructor(
    private router:Router,
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }

  ngOnInit(): void {
    this.dataSource = []
    if (this.listaRecenzija !==undefined){
      this.dataSource = this.listaRecenzija;
    }
  }
  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaRecenzija.currentValue !== undefined){
      this.dataSource = changes.listaRecenzija.currentValue
    }
  }

  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['produktDetaljno/'+serijskiBroj])
  }

}
