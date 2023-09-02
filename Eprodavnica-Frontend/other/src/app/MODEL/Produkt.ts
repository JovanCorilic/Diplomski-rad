import { Recenzija } from "./Recenzija"
import { Tip } from "./Tip"

export class Produkt {
    naziv: string
    deskripcija: string
    serijskiBroj: string
    cena: number
    ocena: number
    datumPravljenja: Date
    akcija: number
    listaTipova: Tip[]
    emailProdavac: string

    constructor(
        naziv: string,
        deskripcija: string,
        serijskiBroj: string,
        cena: number,
        ocena: number,
        datumPravljenja: Date,
        akcija: number,
        listaTipova: Tip[],
        emailProdavac: string
    ) {
        this.naziv = naziv
        this.deskripcija = deskripcija
        this.serijskiBroj = serijskiBroj
        this.cena = cena
        this.ocena = ocena
        this.datumPravljenja = datumPravljenja
        this.akcija = akcija
        this.listaTipova = listaTipova
        this.emailProdavac = emailProdavac
    }
}