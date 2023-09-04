import { HttpHeaders, HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Filter } from "../MODEL/Filter/Filter";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class RecenzijaService{
    private readonly path = "http://localhost:8080/recenzija";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}
    
    public getByPage(page:number,size:number,serijskiBroj:string): Observable<any>{
        return this.http.get(this.path+"/by-page"+`/${serijskiBroj}`+"?page="+page+"&size="+size, {headers:this.headers})
	}

    public filterByPage(filter:Filter, page:number,size:number,serijskiBroj:string):Observable<any>{
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
        return this.http.post(this.path+'/filter-by-page'+`/${serijskiBroj}`,filter, queryParams);
    }

	public getByPageMusterija(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageMusterija?page="+page+"&size="+size, {headers:this.headers})
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
}