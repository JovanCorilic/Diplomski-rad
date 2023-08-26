export class ProduktMini {
    naziv: string
    serijskiBroj: string
    cena: number
    ocena: number
    akcija: number
    constructor(
        naziv: string,
        serijskiBroj: string,
        cena: number,
        ocena: number,
        akcija: number
    ) {
        this.naziv = naziv
        this.serijskiBroj = serijskiBroj
        this.cena = cena
        this.ocena = ocena
        this.akcija = akcija
    }
}