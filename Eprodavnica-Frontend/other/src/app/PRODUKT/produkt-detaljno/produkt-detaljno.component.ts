import { Recenzija } from './../../MODEL/Recenzija';
import { Produkt } from './../../MODEL/Produkt';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, AbstractControl, ValidatorFn, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Filter } from 'src/app/MODEL/Filter/Filter';
import { Ocena } from 'src/app/MODEL/Filter/Ocena';
import { Tip } from 'src/app/MODEL/Tip';
import { ProduktService } from 'src/app/SERVICE/Produkt.service';
import { RecenzijaService } from 'src/app/SERVICE/Recenzija.service';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Artikal } from 'src/app/MODEL/Artikal';
import { RacunService } from 'src/app/SERVICE/Racun.service';
import { MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition, MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-produkt-detaljno',
  templateUrl: './produkt-detaljno.component.html',
  styleUrls: ['./produkt-detaljno.component.css']
})
export class ProduktDetaljnoComponent implements OnInit {
  produkt = <Produkt>{}
  serijskiBroj=<string>{}
  filter = <Filter>{}
  lista:Recenzija[]|undefined;
  ukljucioFilter:boolean = false;
  kolicinaFormControl = new FormControl(1,[this.notANumber(),this.viseOdNula()])
  daLiJeUWishlist:boolean = false
  status: boolean = false;
  status2: boolean = false;
  status3: boolean = false;
  daLiJeUIstorijiKupovine:boolean = false
  daLiJeVecNapravljenaRecenzija:boolean = false

  listaOcena: number[] = [1,2,3,4,5]
  pageSize: number;
  currentPage: number;
  totalSize: number;
  ocenaForm : FormGroup;
  recenzijaFrom:FormGroup;

  recenzija = <Recenzija>{}

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  
  constructor(
    private produktService:ProduktService,
    private recenzijaService:RecenzijaService,
    private racunService:RacunService,
    private route:ActivatedRoute,
    private modalService: NgbModal,
    private fBuilder: FormBuilder,
    private router:Router,
    private _snackBar: MatSnackBar
  ){
    this.pageSize = 2;
		this.currentPage = 1;
		this.totalSize = 1;

    let temp=this.route.snapshot.paramMap.get('serijskiBroj');
    if(temp != null)
        this.serijskiBroj = temp;
    else
      this.serijskiBroj = "nista";

    this.ocenaForm = fBuilder.group({
      ocene: this.fBuilder.array([])
    })

    this.recenzijaFrom = fBuilder.group({
      ocena:"5",
      komentar: ["",[Validators.required]]
    })
  }

  ngOnInit(): void {
    this.produktService.dajProdukt(this.serijskiBroj).subscribe(
      res=>{
        this.produkt = res;
      }
    )

    this.recenzijaService.getByPage(this.currentPage - 1,this.pageSize,this.serijskiBroj).subscribe(
      res =>{
        this.lista = res.content as Recenzija[];
        this.totalSize = Number(res.totalElements);
      }
    )

    for (let i in this.listaOcena){
      this.addOcena();
    }

    if (this.getRole()!=="")
      this.produktService.daLiJeUWishlist(this.serijskiBroj).subscribe(
        res=>{
          this.daLiJeUWishlist = res;
        }
      )

    this.produktService.daLiJeUIstorijiProdukata(this.serijskiBroj).subscribe(
      res=>{
        this.daLiJeUIstorijiKupovine = res;
        this.recenzijaService.daLiImaRecenzijuZaProdukt(this.serijskiBroj).subscribe(
          res=>{
            this.daLiJeVecNapravljenaRecenzija = res;
            if (this.daLiJeVecNapravljenaRecenzija){
              this.recenzijaService.dajRecenziju(this.serijskiBroj).subscribe(
                res=>{
                  this.recenzija = res;
                  this.recenzijaFrom.controls.ocena.setValue(res.ocena);
                  this.recenzijaFrom.controls.komentar.setValue(res.komentar);
                }
              )
            }
          }
        )
      }
    )
  }

  operacijeRecenzije(){
    this.recenzija.ocena = this.recenzijaFrom.value.ocena;
    this.recenzija.komentar = this.recenzijaFrom.value.komentar; 
    this.recenzija.produkt=this.produkt;
    if(this.daLiJeVecNapravljenaRecenzija){
      this.recenzijaService.updateRecenziju(this.recenzija).subscribe(
        res=>{
          this.openSnackBar("Uspešno ažurirana recenzija");
        }
      )
    }else{
      this.recenzijaService.napraviRecenziju(this.recenzija).subscribe(
        res=>{
          this.openSnackBar("Uspešno napravljena recenzija");
        }
      )
    }
  }

  konvertuj(produkt:Produkt,artikal:Artikal):any{
    artikal.cena = produkt.cena;
    artikal.nazivProdukta = produkt.naziv;
    artikal.akcija = produkt.akcija;
    artikal.ukupnaCena = artikal.broj * ( artikal.cena - (artikal.cena*artikal.akcija/100));
    artikal.serijskiBroj = produkt.serijskiBroj;
}

  dodajUKorpu(){
    this.status = !this.status;
    let kolicina = this.kolicinaFormControl.value
    let artikal = <Artikal>{}
    if(kolicina!=null)
      artikal.broj=kolicina
    this.konvertuj(this.produkt,artikal);
    this.racunService.dodajUKorpu(artikal).subscribe(
      res=>{
        this.status = !this.status;
        this.openSnackBar("Dodato u korpu!")
      },
      error =>{
        this.status = !this.status;
      }
    )
  }

  dodajUWishlist(){
    this.status2 = !this.status2;
    this.produktService.dodajuWishlist(this.serijskiBroj).subscribe(
      res=>{
        this.daLiJeUWishlist= true
        this.status2 = !this.status2;
      },
      error =>{
        this.status2 = !this.status2;
      }
    )
  }

  izbaciIzWishlista(){
    this.status2 = !this.status2;
    this.produktService.izbaciIzWishlista(this.serijskiBroj).subscribe(
      res=>{
        this.daLiJeUWishlist= true
        this.status2 = !this.status2;
      },
      error =>{
        this.status2 = !this.status2;
      }
    )
  }

  edit(){
    this.router.navigate(['editProdukta/'+this.serijskiBroj])
  }

  delete(){

  }

  getRole():string{
    const item = sessionStorage.getItem('user');

    if(!item){
      return "";
    }

    const jwt:JwtHelperService = new JwtHelperService();
    const decodedItem = JSON.parse(item!);
    const info = jwt.decodeToken(decodedItem.accessToken);
    return info['uloga'];
  }

  drop(event: CdkDragDrop<Tip[]>) {
    moveItemInArray(this.produkt.listaTipova, event.previousIndex, event.currentIndex);
  }

  resetuj(){
    this.filter = <Filter>{}
    this.ocenaForm.reset();
    this.ukljucioFilter = false;
    this.recenzijaService.getByPage(this.currentPage - 1,this.pageSize,this.serijskiBroj).subscribe(
      res =>{
        this.lista = res.content as Recenzija[];
        this.totalSize = Number(res.totalElements);
      }
    )
  }

  filters(){
    this.status3 = !this.status3;
    this.filter.ocena = []
    for( let i in this.listaOcena ){
      this.filter.ocena.push(new Ocena(this.listaOcena[i],this.ocenaForm.value.ocene.at(i).ocena));
    }
    this.recenzijaService.filterByPage(this.filter,this.currentPage-1,this.pageSize,this.serijskiBroj).subscribe(
      res =>{
        this.lista = res.body.content as Recenzija[];
        this.totalSize = Number(res.body.totalElements);
        this.ukljucioFilter = true;
        this.status3 = !this.status3;
      },
      error =>{
        this.status3 = !this.status3;
      }
    )
  }

  changePage(newPage: number) {
    if (this.ukljucioFilter){
      this.recenzijaService.filterByPage(this.filter,newPage-1,this.pageSize,this.serijskiBroj).subscribe(
        res =>{
          this.lista = res.body.content as Recenzija[];
          this.totalSize = Number(res.body.totalElements);
        }
      )
    }else {
      this.recenzijaService.getByPage(newPage - 1,this.pageSize,this.serijskiBroj).subscribe(
        res=>{
          this.lista = res.content as Recenzija[];
          this.totalSize = Number(res.totalElements);
        }
      )
    }
  }

  open(content:any) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then( 
    result =>  { 
      
    }, (reason) => {
      
    });
  }

  get ocene() : FormArray {
    return this.ocenaForm.get("ocene") as FormArray
  }

  newOcena(): FormGroup {
    return this.fBuilder.group({
      ocena:false
    })
  }

  addOcena(){
    this.ocene.push(this.newOcena());
  }

  getErrorMessage(temp:any) {
    if (temp.hasError('notANumber')) {
      return 'Uneta vrednost nije broj';
    }
    else
      return temp.hasError('viseOdNula') ? 'Količina mora biti više od 0' : '';
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

  openSnackBar(poruka:string) {
    this._snackBar.open(poruka, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

}
