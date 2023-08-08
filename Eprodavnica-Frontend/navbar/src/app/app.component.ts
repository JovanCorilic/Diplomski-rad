import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'navbar-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'navbar';

  constructor(
    private router:Router
  ){}

  goToHome() {
    this.router.navigate(['']);
  }

  goToLogin(){
    this.router.navigate(['#/home']);
  }

  register(){
    this.router.navigate(['#/other']);
  }
}
