import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { Artikal } from 'src/app/MODEL/Artikal';

@Component({
  selector: 'app-tabela-artikal',
  templateUrl: './tabela-artikal.component.html',
  styleUrls: ['./tabela-artikal.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class TabelaArtikalComponent implements OnInit, OnChanges{
  @Input() listaArtikala:Artikal[]|undefined;

  dataSource:Artikal[] = []
  columnsToDisplay = ['broj'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement = <Artikal>{}

  constructor(
    private router:Router,
  ){
    if (this.listaArtikala !== undefined)
      this.dataSource = this.listaArtikala
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }
  ngOnInit(): void {
    this.dataSource = []
    if (this.listaArtikala !== undefined)
      this.dataSource = this.listaArtikala
  }
  ngOnChanges(changes: any): void {
    this.dataSource = []
    if (changes.listaArtikala.currentValue !==undefined)
      this.dataSource = changes.listaArtikala.currentValue
  }

  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['produktDetaljno/'+serijskiBroj])
  }
}
