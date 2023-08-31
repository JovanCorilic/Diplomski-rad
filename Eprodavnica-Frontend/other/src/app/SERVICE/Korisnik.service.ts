import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Musterija } from "../MODEL/Musterija";
import { Prodavac } from "../MODEL/Prodavac";

@Injectable({ providedIn: 'root' })
export class KorisnikService{
    private readonly path = "http://localhost:8080/korisnik";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}

    public dajMusteriju():Observable<Musterija>{
        return this.http.get<Musterija>(this.path+"/dajMusteriju");
    }

    public dajProdavac():Observable<Prodavac>{
        return this.http.get<Prodavac>(this.path+"/dajProdavac");
    }
}