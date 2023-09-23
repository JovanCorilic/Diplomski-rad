import { Artikal } from "./Artikal";

export class Racun {
    konacnaCena: number
    brojRacuna: string
    emailMusterija: string
    datumKreiranja: Date
    korpa: boolean
    constructor(
        konacnaCena: number,
        brojRacuna: string,
        emailMusterija: string,
        datumKreiranja: Date,
        korpa: boolean
    ) {
        this.konacnaCena = konacnaCena
        this.brojRacuna = brojRacuna
        this.emailMusterija = emailMusterija
        this.datumKreiranja = datumKreiranja
        this.korpa = korpa
    }
}