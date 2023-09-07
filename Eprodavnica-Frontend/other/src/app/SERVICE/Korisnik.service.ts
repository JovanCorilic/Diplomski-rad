import { HttpHeaders, HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Musterija } from "../MODEL/Musterija";
import { Prodavac } from "../MODEL/Prodavac";
import { Filter } from "../MODEL/Filter/Filter";
import { Korisnik } from "../MODEL/Korisnik";
import { PravljenjeAdmina } from "../MODEL/PravljanjeAdmina";

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

	public dajAdmin():Observable<Korisnik>{
        return this.http.get<Korisnik>(this.path+"/dajAdmin");
    }

    public updateKorisnik(korisnik:Musterija){
        return this.http.put(this.path+"/update",korisnik);
    }

    public getByPageMusterija(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageMusterija?page="+page+"&size="+size, {headers:this.headers})
	}

    public getByPageProdavac(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageProdavac?page="+page+"&size="+size, {headers:this.headers})
	}

	public getByPageAdmin(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageAdmin?page="+page+"&size="+size, {headers:this.headers})
	}

    public filterByPageMusterija(filter:Korisnik, page:number,size:number):Observable<any>{
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

    public filterByPageProdavac(filter:Korisnik, page:number,size:number):Observable<any>{
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
        return this.http.post(this.path+'/filter-by-pageProdavac',filter, queryParams);
    }

	public filterByPageAdmin(filter:Korisnik, page:number,size:number):Observable<any>{
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

	public povuciKorisnika(email:string){
		return this.http.delete(this.path+"/povuciKorisnika"+`/${email}`)
	}

	public createAdmin(admin:PravljenjeAdmina){
		return this.http.post(this.path+"/pravljenjeAdmina",admin);
	}
}