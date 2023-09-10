import { HttpHeaders, HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Produkt } from "../MODEL/Produkt";
import { Observable } from "rxjs";
import { Filter } from "../MODEL/Filter/Filter";

@Injectable({ providedIn: 'root' })
export class ProduktService{
    private readonly path = "http://localhost:8080/produkt";
    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    constructor(private http: HttpClient){}

    public dajProdukt(serijskiBroj:string):Observable<Produkt>{
        return this.http.get<Produkt>(this.path+"/get"+`/${serijskiBroj}`);
    }

    public dodajuWishlist(serijskiBroj:string){
        return this.http.post(this.path+"/dodajuWishlist",serijskiBroj);
    }

    public daLiJeUWishlist(serijskiBroj:string):Observable<boolean>{
        return this.http.get<boolean>(this.path+"/daLiJeUWishlist"+`/${serijskiBroj}`)
    }

    public napraviProdukt(produkt:Produkt){
        return this.http.post(this.path+"/create",produkt);
    }

    public updateProdukt(produkt:Produkt, serijskiBroj:string){
        return this.http.put(this.path+"/update"+`/${serijskiBroj}`,produkt);
    }
    
    public getByPageIstorijaProdukata(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageIstorijaProdukata?page="+page+"&size="+size, {headers:this.headers})
	}

    public filterByPageIstorijaProdukata(filter:Filter, page:number,size:number):Observable<any>{
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
        return this.http.post(this.path+'/filter-by-pageIstorijaProdukata',filter, queryParams);
    }

    public getByPageWishlist(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageWishlist?page="+page+"&size="+size, {headers:this.headers})
	}

    public filterByPageWishlist(filter:Filter, page:number,size:number):Observable<any>{
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
        return this.http.post(this.path+'/filter-by-pageWishlist',filter, queryParams);
    }

    public getByPageProdavac(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageProdavac?page="+page+"&size="+size, {headers:this.headers})
	}

    public filterByPageProdavac(filter:Filter, page:number,size:number):Observable<any>{
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

    public izbaciIzWishlista(serijskiBroj:string){
        return this.http.post(this.path+"/izbaciIzWishlista",serijskiBroj);
    }

    public daLiJeUIstorijiProdukata(serijskiBroj:string):Observable<boolean>{
        return this.http.get<boolean>(this.path+"/daLiJeUIstorijiProdukata"+`/${serijskiBroj}`);
    }
}