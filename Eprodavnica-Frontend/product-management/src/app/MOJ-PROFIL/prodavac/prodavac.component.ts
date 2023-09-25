import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Prodavac } from 'src/app/MODEL/Prodavac';
import { KorisnikService } from 'src/app/SERVICE/Korisnik.service';

@Component({
  selector: 'app-prodavac',
  templateUrl: './prodavac.component.html',
  styleUrls: ['./prodavac.component.css']
})
export class ProdavacComponent implements OnInit{

  constructor(

  ){

  }

  ngOnInit(): void {

  }

}
