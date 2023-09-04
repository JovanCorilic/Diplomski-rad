import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { Produkt } from 'src/app/MODEL/Produkt';

@Component({
  selector: 'app-tabela-produkata',
  templateUrl: './tabela-produkata.component.html',
  styleUrls: ['./tabela-produkata.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class TabelaProdukataComponent implements OnInit, OnChanges{
  @Input() listaProdukata:Produkt[]|undefined;

  dataSource:Produkt[] = []
  columnsToDisplay = ['naziv', 'cena', 'ocena'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement = <Produkt>{}

  constructor(
    private router:Router,
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }
  ngOnInit(): void {
    this.dataSource = []
    if (this.listaProdukata !== undefined)
      this.dataSource = this.listaProdukata
  }
  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaProdukata.currentValue !== undefined)
      this.dataSource = changes.listaProdukata.currentValue
  }

  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['produktDetaljno/'+serijskiBroj])
  }
}
