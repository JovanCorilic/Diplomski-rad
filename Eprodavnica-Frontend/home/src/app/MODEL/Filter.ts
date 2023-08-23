import { Cena } from "./Cena";
import { Ocena } from "./Ocena";
import { Tip } from "./Tip";

export class Filter {
    cena: Cena
    tip: Tip[]
    ocena: Ocena[]
    constructor(cena: Cena, tip: Tip[], ocena: Ocena[]) {
        this.cena = cena
        this.tip = tip
        this.ocena = ocena
    }
}