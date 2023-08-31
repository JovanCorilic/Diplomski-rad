import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Tip } from "../MODEL/Tip";
import { TipFilter } from "../MODEL/Filter/TipFilter";

@Injectable({ providedIn: 'root' })
export class TipService{
    private readonly path = "http://localhost:8080/tip";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}
    
    public getAllTip():Observable<TipFilter[]>{
        return this.http.get<TipFilter[]>(this.path+"/getAll");
    }

    public getAllTipNormalno():Observable<Tip[]>{
        return this.http.get<Tip[]>(this.path+"/getAllNormalno");
    }
}