import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TipFilter } from 'src/app/MODEL/Filter/TipFilter';
import { ImageModel } from 'src/app/MODEL/ImageModel';
import { Produkt } from 'src/app/MODEL/Produkt';
import { Tip } from 'src/app/MODEL/Tip';
import { ProduktService } from 'src/app/SERVICE/Produkt.service';
import { TipService } from 'src/app/SERVICE/Tip.service';

@Component({
  selector: 'app-produkt-create',
  templateUrl: './produkt-create.component.html',
  styleUrls: ['./produkt-create.component.css']
})
export class ProduktCreateComponent implements OnInit{
  produktForm: FormGroup
  produkt = <Produkt>{}
  listaTipova: TipFilter[] = [];
  status:boolean= false

  imaSliku:boolean =false

  selectedFile!: File;

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private router:Router,
    private produktService:ProduktService,
    private fBuilder: FormBuilder,
    private modalService: NgbModal,
    private tipService:TipService,
    private _snackBar: MatSnackBar
  ){
    this.produktForm = fBuilder.group({
      naziv: ["",[Validators.required]],
      deskripcija: ["",[Validators.required]],
      cena: ["",[Validators.required,this.notANumber(),this.viseOdNula()]],
      tipovi: this.fBuilder.array([])
    })
  }

  ngOnInit(): void {
    this.tipService.getAllTip().subscribe(
      res=>{
        this.listaTipova = res;
        for( let i in this.listaTipova ){
          this.addTip();
        }
      }
    )
  }

  public onFileChanged(event:any) {
    //Select File
    this.selectedFile = event.target.files[0];
    let temp = this.selectedFile.name.split(".")
    let tip = temp[temp.length-1]
    if (tip === "jpeg" || tip === "png" || tip==="jpg"){
      this.openSnackBar("Postavljen fajl")
      this.produkt.slika = <ImageModel>{}
      this.produkt.slika.name = this.selectedFile.name;
    }
    else{
      this.openSnackBar("Fajl mora biti u formatu jpeg ili png!")
    }
  }

  create(){
    this.status = !this.status
    this.produkt.naziv = this.produktForm.value.naziv;
    this.produkt.deskripcija = this.produktForm.value.deskripcija;
    this.produkt.cena = this.produktForm.value.cena;

    this.produkt.listaTipova = []
    for( let i in this.listaTipova ){
      if (this.produktForm.value.tipovi.at(i).tip){
        this.produkt.listaTipova.push(new Tip(this.listaTipova[i].naziv));
      }
    }

    this.produktService.napraviProdukt(this.produkt,this.selectedFile).subscribe(
      res=>{
        this.status = !this.status
        this.openSnackBar("Uspešno napravljen produkt!")
        this.router.navigate(['editProdukta/'+res.serijskiBroj])
      },
      error=>{
        this.status = !this.status
      }
    )
  }

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  getErrorMessage(temp:any) {
    if (temp.hasError('required')) {
      return 'Morate uneti vrednost';
    }
    else if (temp.hasError('notANumber')) {
      return 'Morate uneti broj';
    }
    else
    return temp.hasError('viseOdNula') ? 'Mora biti više od 0' : '';
  }

  get tipovi() : FormArray {
    return this.produktForm.get("tipovi") as FormArray
  }

  newTip(): FormGroup {
    return this.fBuilder.group({
      tip:false
    })
  }

  addTip() {
    this.tipovi.push(this.newTip());
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  viseOdNula():ValidatorFn{
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      if (typeof value == 'string') {
        nV = value.replace(',', '.')
      }
      return (!Number.isNaN(Number(nV)) && !control.pristine && (Number(nV)<=0)) ? {viseOdNula: true} : null;
    };
  }

  notANumber(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const value = control.value
      let nV = value
      if (typeof value == 'string') {
        nV = value.replace(',', '.')
      }
      return (Number.isNaN(Number(nV)) && !control.pristine) ? {notANumber: true} : null;
    };
  }

}

function readFileAsArrayBuffer(file: File): Promise<ArrayBuffer> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();

    reader.onload = () => {
      if (reader.result instanceof ArrayBuffer) {
        resolve(reader.result);
      } else {
        reject(new Error('Failed to read file as ArrayBuffer.'));
      }
    };

    reader.onerror = () => {
      reject(new Error('Error reading file.'));
    };

    reader.readAsArrayBuffer(file);
  });
}

function arrayBufferToByteArray(buffer: ArrayBuffer): Uint8Array {
  return new Uint8Array(buffer);
}

async function convertFileToByteArray(file: File): Promise<Uint8Array> {
  try {
    const arrayBuffer = await readFileAsArrayBuffer(file);
    const byteArray = arrayBufferToByteArray(arrayBuffer);
    return byteArray;
  } catch (error) {
    console.error('Error converting file to byte array:', error);
    throw error;
  }
}

