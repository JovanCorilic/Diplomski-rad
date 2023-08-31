import { ProduktMini } from "./ProduktMini"
import { Racun } from "./Racun"
import { Recenzija } from "./Recenzija"
import { Tip } from "./Tip"

export class Musterija {
    ime: string
    prezime: string
    email: string
    constructor(ime: string, prezime: string, email: string) {
        this.ime = ime
        this.prezime = prezime
        this.email = email
    }
}