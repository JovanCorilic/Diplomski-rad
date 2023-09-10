export class Korisnik {
    ime: string
    prezime: string
    email: string
    odobrenOdAdmina: boolean
    constructor(
        ime: string,
        prezime: string,
        email: string,
        odobrenOdAdmina: boolean
    ) {
        this.ime = ime
        this.prezime = prezime
        this.email = email
        this.odobrenOdAdmina = odobrenOdAdmina
    }
}