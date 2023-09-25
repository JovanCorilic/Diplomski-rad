import { Injectable, resolveForwardRef } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class Interceptor implements HttpInterceptor{

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const item = sessionStorage.getItem('accessToken');

        if (item) {
            req = req.clone({
				setHeaders: {
				  Authorization: `Bearer ${item}`
				},
				withCredentials: true
			  });
        }

        return next.handle(req);
    }
    
}