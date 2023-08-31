import { Artikal } from "./Artikal";

export class Racun {
    konacnaCena: number
    brojRacuna: string
    emailMusterija: string
    datumKreiranja: Date
    constructor(
        konacnaCena: number,
        brojRacuna: string,
        emailMusterija: string,
        datumKreiranja: Date
    ) {
        this.konacnaCena = konacnaCena
        this.brojRacuna = brojRacuna
        this.emailMusterija = emailMusterija
        this.datumKreiranja = datumKreiranja
    }
}