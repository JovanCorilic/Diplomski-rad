import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Musterija } from 'src/app/MODEL/Musterija';
import { KorisnikService } from 'src/app/SERVICE/Korisnik.service';


@Component({
  selector: 'app-musterija',
  templateUrl: './musterija.component.html',
  styleUrls: ['./musterija.component.css']
})
export class MusterijaComponent implements OnInit{

  constructor(

  ){

  }

  ngOnInit(): void {

  }

}
