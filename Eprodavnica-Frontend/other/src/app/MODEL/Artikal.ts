import { ProduktMini } from "./ProduktMini"

export class Artikal {
    broj: number
    produkt: ProduktMini
    constructor(broj: number, produkt: ProduktMini) {
        this.broj = broj
        this.produkt = produkt
    }
}