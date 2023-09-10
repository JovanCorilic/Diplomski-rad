import { HttpHeaders, HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Tip } from "../MODEL/Tip";
import { TipFilter } from "../MODEL/Filter/TipFilter";
import { Filter } from "../MODEL/Filter/Filter";

@Injectable({ providedIn: 'root' })
export class TipService{
    private readonly path = "http://localhost:8080/tip";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}
    
    public getAllTip():Observable<TipFilter[]>{
        return this.http.get<TipFilter[]>(this.path+"/getAll");
    }

    public getAllTipNormalno():Observable<Tip[]>{
        return this.http.get<Tip[]>(this.path+"/getAll");
    }

    public getByPage(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-page?page="+page+"&size="+size, {headers:this.headers})
	}
    
    public filterByPage(filter:Filter, page:number,size:number):Observable<any>{
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
        return this.http.post(this.path+'/filter-by-page',filter, queryParams);
    }

    public createTip(tip:Tip){
        return this.http.post(this.path+"/create",tip)
    }

    public updateTip(tip:Tip){
        return this.http.put(this.path+"/update"+`/${tip.naziv}`,tip)
    }
}