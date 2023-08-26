import { ProduktMini } from "./ProduktMini"

export class Prodavac {
    ime: string
    prezime: string
    email: string
    listaProdukata: ProduktMini[]
    constructor(
        ime: string,
        prezime: string,
        email: string,
        listaProdukata: ProduktMini[]
    ) {
        this.ime = ime
        this.prezime = prezime
        this.email = email
        this.listaProdukata = listaProdukata
    }
}