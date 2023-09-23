import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';


@Injectable({
	providedIn: 'root'
})
export class LoginGuard implements CanActivate {
    constructor(

		public router: Router
	) { }

	canActivate(): boolean {
		if (this.isLoggedIn()) {
			this.router.navigate(['/']);
			return false;
		}
		return true;
	}

	isLoggedIn(): boolean {
		if (!sessionStorage.getItem('user')) {
				return false;
		}
		return true;
    }
}