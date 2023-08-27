import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Produkt } from "../MODEL/Produkt";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class ProduktService{
    private readonly path = "http://localhost:8080/produkt";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}

    public dajProdukt(serijskiBroj:string):Observable<Produkt>{
        return this.http.get<Produkt>(this.path+"/get"+`/${serijskiBroj}`);
    }
    
}