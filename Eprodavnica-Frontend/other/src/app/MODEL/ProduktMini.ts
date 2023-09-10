export class ProduktMini {
    naziv: string
    serijskiBroj: string
    cena: number
    ocena: number
    akcija: number
    odobrenOdAdmina: boolean
    constructor(
        naziv: string,
        serijskiBroj: string,
        cena: number,
        ocena: number,
        akcija: number,
        odobrenOdAdmina: boolean
    ) {
        this.naziv = naziv
        this.serijskiBroj = serijskiBroj
        this.cena = cena
        this.ocena = ocena
        this.akcija = akcija
        this.odobrenOdAdmina = odobrenOdAdmina
    }
}