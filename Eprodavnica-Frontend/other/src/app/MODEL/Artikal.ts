export class Artikal {
    id: number
    broj: number
    nazivProdukta: string
    cena: number
    akcija: number
    ukupnaCena: number
    constructor(
        id: number,
        broj: number,
        nazivProdukta: string,
        cena: number,
        akcija: number,
        ukupnaCena: number
    ) {
        this.id = id
        this.broj = broj
        this.nazivProdukta = nazivProdukta
        this.cena = cena
        this.akcija = akcija
        this.ukupnaCena = ukupnaCena
    }
}