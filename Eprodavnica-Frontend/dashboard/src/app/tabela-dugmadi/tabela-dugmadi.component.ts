import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ProduktMini } from '../MODEL/ProduktMini';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tabela-dugmadi',
  templateUrl: './tabela-dugmadi.component.html',
  styleUrls: ['./tabela-dugmadi.component.css']
})
export class TabelaDugmadiComponent implements OnInit, OnChanges{
  @Input() listaProdukataMini:ProduktMini[]|undefined;

  mapa:Map<string,{name:string,retrievedImage:any}>;

  constructor(
    private router:Router,
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
    this.mapa = new Map();
  }

  ngOnInit(): void {
    this.mapa = new Map();
    this.listaProdukataMini?.forEach(element=>{
      let listaTemp = element.slika.name.split('.');
      this.mapa.set(element.serijskiBroj, {name:element.slika.name, retrievedImage:'data:image/'+listaTemp[listaTemp.length-1]+';base64,' + element.slika.picByte});
    })
  }
  ngOnChanges(changes: any): void {
    this.mapa = new Map();
    let lista:ProduktMini[] = changes.listaProdukataMini.currentValue;
    lista?.forEach(element=>{
      let listaTemp = element.slika.name.split('.');
      this.mapa.set(element.serijskiBroj, {name:element.slika.name, retrievedImage:'data:image/'+listaTemp[listaTemp.length-1]+';base64,' + element.slika.picByte});
    })
  }



  idiNaProdukt(serijskiBroj:string){
    this.router.navigate(['/other/produktDetaljno/'+serijskiBroj]);
  }

}
