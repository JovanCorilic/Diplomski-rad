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

    public napraviProdukt(produkt:Produkt,file:File):Observable<Produkt>{
        const formData:FormData=new FormData();
        formData.append('File',file,file.name);

        const myObjStr = JSON.stringify(produkt); 
        const userBlob = new Blob([myObjStr],{ type: "application/json"});
        formData.append('produkt', userBlob);

        return this.http.post<Produkt>(this.path+"/create", formData);
    }

    public updateProdukt(produkt:Produkt, serijskiBroj:string, file:File):Observable<Produkt>{
        const formData:FormData=new FormData();
        formData.append('File',file,file.name);

        const myObjStr = JSON.stringify(produkt); 
        const userBlob = new Blob([myObjStr],{ type: "application/json"});
        formData.append('produkt', userBlob);

        return this.http.put<Produkt>(this.path+"/update"+`/${serijskiBroj}`,formData);
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

    public getByPageAdmin(page:number,size:number): Observable<any>{
        return this.http.get(this.path+"/by-pageAdmin?page="+page+"&size="+size, {headers:this.headers})
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

    public izbaciIzWishlista(serijskiBroj:string){
        return this.http.post(this.path+"/izbaciIzWishlista",serijskiBroj);
    }

    public daLiJeUIstorijiProdukata(serijskiBroj:string):Observable<boolean>{
        return this.http.get<boolean>(this.path+"/daLiJeUIstorijiProdukata"+`/${serijskiBroj}`);
    }

    public povuciProizvodAdmin(serijskiBroj:string){
        return this.http.put(this.path+"/povuciProduktAdmin",serijskiBroj);
    }

    public povuciProizvodProdavac(serijskiBroj:string){
        return this.http.put(this.path+"/povuciProduktProdavac",serijskiBroj);
    }

    public vratiProizvodAdmin(serijskiBroj:string){
        return this.http.put(this.path+"/vratiProduktAdmin",serijskiBroj);
    }

    public vratiProizvodProdavac(serijskiBroj:string){
        return this.http.put(this.path+"/vratiProduktProdavac",serijskiBroj);
    }

    public dodajAkciju(serijskiBroj:string,broj:number){
        return this.http.put(this.path+"/dodajAkciju"+`/${broj}`,serijskiBroj);
    }
}