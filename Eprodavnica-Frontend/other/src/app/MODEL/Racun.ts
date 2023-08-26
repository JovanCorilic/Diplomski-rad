import { Artikal } from "./Artikal";

export class Racun {
    konacnaCena: number
    brojRacuna: string
    artikals: Artikal[]
    emailMusterija: string
    constructor(
        konacnaCena: number,
        brojRacuna: string,
        artikals: Artikal[],
        emailMusterija: string
    ) {
        this.konacnaCena = konacnaCena
        this.brojRacuna = brojRacuna
        this.artikals = artikals
        this.emailMusterija = emailMusterija
    }
}