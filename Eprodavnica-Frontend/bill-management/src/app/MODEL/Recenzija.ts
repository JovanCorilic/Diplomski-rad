import { ProduktMini } from "./ProduktMini"

export class Recenzija {
    id: number
    ocena: number
    komentar: string
    datumPravljenja: Date
    emailMustarija: string
    produkt: ProduktMini
    constructor(
        id: number,
        ocena: number,
        komentar: string,
        datumPravljenja: Date,
        emailMustarija: string,
        produkt: ProduktMini
    ) {
        this.id = id
        this.ocena = ocena
        this.komentar = komentar
        this.datumPravljenja = datumPravljenja
        this.emailMustarija = emailMustarija
        this.produkt = produkt
    }
}