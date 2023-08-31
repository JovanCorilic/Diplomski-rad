import { HttpHeaders, HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Artikal } from "../MODEL/Artikal";
import { Racun } from "../MODEL/Racun";
import { Filter } from "../MODEL/Filter/Filter";

@Injectable({ providedIn: 'root' })
export class RacunService{
    private readonly path = "http://localhost:8080/racun";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}

    public dodajUKorpu(artikal:Artikal){
        return this.http.post(this.path+"/dodajArtikal",artikal);
    }

    public getByPageMusterija(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageMusterija?page="+page+"&size="+size, {headers:this.headers})
	}

    public getByPageAdmin(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageAdmin?page="+page+"&size="+size, {headers:this.headers})
	}

    public filterByPageMusterija(filter:Filter, page:number,size:number):Observable<any>{
        let queryParams = {};

		queryParams = {
			headers:new HttpHeaders({
				'Content-Type': 'application/json'
			}),
			observe: 'response',
			params: new HttpParams()
				.set('page', String(page))
				.append('size', String(size)),
		};
		const headeri=new HttpHeaders({
			'Content-Type': 'application/json'
		});
        return this.http.post(this.path+'/filter-by-pageMusterija',filter, queryParams);
    }

    public filterByPageAdmin(filter:Filter, page:number,size:number):Observable<any>{
        let queryParams = {};

		queryParams = {
			headers:new HttpHeaders({
				'Content-Type': 'application/json'
			}),
			observe: 'response',
			params: new HttpParams()
				.set('page', String(page))
				.append('size', String(size)),
		};
		const headeri=new HttpHeaders({
			'Content-Type': 'application/json'
		});
        return this.http.post(this.path+'/filter-by-pageAdmin',filter, queryParams);
    }
    
}