import { Produkt } from "./Produkt"

export class Artikal {
    id: number
    broj: number
    nazivProdukta: string
    cena: number
    akcija: number
    ukupnaCena: number
    serijskiBroj: string;
    constructor(
        id: number,
        broj: number,
        nazivProdukta: string,
        cena: number,
        akcija: number,
        ukupnaCena: number,
        serijskiBroj: string
    ) {
        this.id = id
        this.broj = broj
        this.nazivProdukta = nazivProdukta
        this.cena = cena
        this.akcija = akcija
        this.ukupnaCena = ukupnaCena
        this.serijskiBroj = serijskiBroj
    }

    public konvertuj(produkt:Produkt):any{
        this.cena = produkt.cena;
        this.nazivProdukta = produkt.naziv;
        this.akcija = produkt.akcija;
        this.ukupnaCena = this.broj * ( this.cena - (this.cena*this.akcija/100));
        this.serijskiBroj = produkt.serijskiBroj;
    }
}