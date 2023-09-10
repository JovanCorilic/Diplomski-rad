import { HttpHeaders, HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Filter } from "../MODEL/Filter/Filter";
import { Observable } from "rxjs";
import { Recenzija } from "../MODEL/Recenzija";

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

	public ukloniRecenziju(id:number){
		return this.http.delete(this.path+"/delete"+`/${id}`);
	}

	public dajRecenziju(serijskiBroj:string):Observable<Recenzija>{
		return this.http.get<Recenzija>(this.path+"/dajRecenzijuMusterije"+`/${serijskiBroj}`);
	}

	public napraviRecenziju(recenzija:Recenzija){
		return this.http.post(this.path+"/create",recenzija);
	}

	public updateRecenziju(recenzija:Recenzija){
		return this.http.put(this.path+"/update"+`/${recenzija.id}`,recenzija);
	}

	public daLiImaRecenzijuZaProdukt(serijskiBroj:string):Observable<boolean>{
		return this.http.get<boolean>(this.path+"/daLiImaRecenzijuZaProdukt"+`/${serijskiBroj}`);
	}
}