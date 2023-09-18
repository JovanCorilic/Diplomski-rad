import { ImageModel } from "./ImageModel"

export class ProduktMini {
    naziv: string
    serijskiBroj: string
    cena: number
    ocena: number
    akcija: number
    odobrenOdAdmina: boolean
    slika: ImageModel
    constructor(
        naziv: string,
        serijskiBroj: string,
        cena: number,
        ocena: number,
        akcija: number,
        odobrenOdAdmina: boolean,
        slika: ImageModel
    ) {
        this.naziv = naziv
        this.serijskiBroj = serijskiBroj
        this.cena = cena
        this.ocena = ocena
        this.akcija = akcija
        this.odobrenOdAdmina = odobrenOdAdmina
        this.slika = slika
    }
}