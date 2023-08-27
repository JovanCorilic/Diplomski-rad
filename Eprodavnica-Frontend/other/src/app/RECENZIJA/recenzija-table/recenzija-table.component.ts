import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Recenzija } from 'src/app/MODEL/Recenzija';

@Component({
  selector: 'app-recenzija-table',
  templateUrl: './recenzija-table.component.html',
  styleUrls: ['./recenzija-table.component.css']
})
export class RecenzijaTableComponent {
  @Input() listaRecenzija:Recenzija[]|undefined;

  constructor(
    private router:Router,
  ){
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }
}
