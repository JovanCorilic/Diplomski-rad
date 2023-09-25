import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Musterija } from '../MODEL/Musterija';
import { Racun } from '../MODEL/Racun';

@Injectable({
	providedIn: 'root'
})
export class AuthenticationService {
    private headers = new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    });

    private path = "http://localhost:8080/auth";
    constructor(
        private http: HttpClient
    ) { }

    login(auth: any): Observable<any> {
            return this.http.post(this.path+'/log-in', {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'json',withCredentials:true});
	}

	logout(): Observable<any> {
		return this.http.get(this.path+'/log-out', {headers: this.headers, responseType: 'text'});
	}

    register(musterija:Musterija){
		return this.http.post(this.path+"/register",musterija);
	}

    posaljiZaPromenuSifre(email:string){
        return this.http.post(this.path+"/promeniLozinkuSlanjeEmail",email);
    }

    verifikacijaRegistracije(token:string){
        return this.http.get(this.path+"/verifikacijaRegistracijaMusterija"+`/${token}`);
    }

	verifikacijaAdminNalog(token:string){
        return this.http.get(this.path+"/verifikacijaAdminNalog"+`/${token}`);
    }

	isLoggedIn(): boolean {
		if (!sessionStorage.getItem('user')) {
				return false;
		}
		return true;
    }

    dajAktivanRacun():Observable<Racun>{
        return this.http.get<Racun>("http://localhost:8080/racun/dajAktivanRacun")
    }

    promeniLozinku(token:string,lozinka:string){
        return this.http.post(this.path+"/promeniLozinku"+`/${token}`,lozinka);
    }
}