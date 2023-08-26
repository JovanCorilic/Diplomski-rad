export class Recenzija {
    id: number
    ocena: number
    komentar: string
    datumPravljenja: Date
    emailMustarija: string
    serijskiBrojProdukt: string
    constructor(
        id: number,
        ocena: number,
        komentar: string,
        datumPravljenja: Date,
        emailMustarija: string,
        serijskiBrojProdukt: string
    ) {
        this.id = id
        this.ocena = ocena
        this.komentar = komentar
        this.datumPravljenja = datumPravljenja
        this.emailMustarija = emailMustarija
        this.serijskiBrojProdukt = serijskiBrojProdukt
    }
}