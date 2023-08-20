import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ProduktMini } from './MODEL/ProduktMini';
import { ProduktMiniService } from './SERVICE/ProduktMini.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'home';
  lista:ProduktMini[]|undefined;
  pageSize: number;
  currentPage: number;
  totalSize: number;

  constructor(
    private router:Router,
    private fBuilder: FormBuilder,
    private produktMiniService:ProduktMiniService
  ){
    this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;
  }

  ngOnInit(): void {
    this.produktMiniService.getByPage(this.currentPage - 1,this.pageSize).subscribe(
      res =>{
        this.lista = res.content as ProduktMini[];
        this.totalSize = Number(res.totalElements);
      }
    )
  }

  changePage(newPage: number) {
    this.produktMiniService.getByPage(newPage - 1,this.pageSize).subscribe(
      res=>{
        this.lista = res.content as ProduktMini[];
        this.totalSize = Number(res.totalElements);
      }
    )
  }

}
