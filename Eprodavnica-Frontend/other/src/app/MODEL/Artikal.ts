import { Produkt } from "./Produkt"
import { ProduktMini } from "./ProduktMini"

export class Artikal {
    broj: number
    produkt: ProduktMini
    constructor(broj: number, produkt: ProduktMini) {
        this.broj = broj
        this.produkt = produkt
    }

    konvertuj(produktVeliki:Produkt){
        this.produkt.serijskiBroj = produktVeliki.serijskiBroj
        this.produkt.ocena = produktVeliki.ocena
        this.produkt.naziv=produktVeliki.naziv
        this.produkt.cena=produktVeliki.cena
        this.produkt.akcija=produktVeliki.akcija
    }
}