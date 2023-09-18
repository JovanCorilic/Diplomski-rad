export class Musterija {
    ime: string
    prezime: string
    email: string
    lozinka: string
    uloga: string
    constructor(
        ime: string,
        prezime: string,
        email: string,
        lozinka: string,
        uloga: string
    ) {
        this.ime = ime
        this.prezime = prezime
        this.email = email
        this.lozinka = lozinka
        this.uloga = uloga
    }

}