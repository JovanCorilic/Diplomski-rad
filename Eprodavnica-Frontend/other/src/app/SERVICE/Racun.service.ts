import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Artikal } from "../MODEL/Artikal";

@Injectable({ providedIn: 'root' })
export class RacunService{
    private readonly path = "http://localhost:8080/racun";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}

    public dodajUKorpu(artikal:Artikal){
        return this.http.post(this.path+"/dodajArtikal",artikal);
    }

    
    
}