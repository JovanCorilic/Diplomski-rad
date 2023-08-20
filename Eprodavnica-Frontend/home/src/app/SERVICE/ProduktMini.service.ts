import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";

@Injectable({ providedIn: 'root' })
export class ProduktMiniService{
    private readonly path = "http://localhost:8080/produkt";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}

    public getByPage(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-page?page="+page+"&size="+size, {headers:this.headers})
	}
}