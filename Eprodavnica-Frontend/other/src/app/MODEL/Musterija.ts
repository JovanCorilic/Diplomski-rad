import { ProduktMini } from "./ProduktMini"
import { Racun } from "./Racun"
import { Recenzija } from "./Recenzija"
import { Tip } from "./Tip"

export class Musterija {
    ime: string
    prezime: string
    email: string
    lozinka: string
    listaTipova: Tip[]
    listaRacuna: Racun[]
    listaRecenzija: Recenzija[]
    wishlist: ProduktMini[]
    constructor(
        ime: string,
        prezime: string,
        email: string,
        lozinka: string,
        listaTipova: Tip[],
        listaRacuna: Racun[],
        listaRecenzija: Recenzija[],
        wishlist: ProduktMini[]
    ) {
        this.ime = ime
        this.prezime = prezime
        this.email = email
        this.lozinka = lozinka
        this.listaTipova = listaTipova
        this.listaRacuna = listaRacuna
        this.listaRecenzija = listaRecenzija
        this.wishlist = wishlist
    }
}