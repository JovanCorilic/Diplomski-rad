import { Cena } from "./Cena";
import { Ocena } from "./Ocena";
import { Tip } from "./Tip";

export class Filter {
    naziv: string
    cena: Cena
    tip: Tip[]
    ocena: Ocena[]
    constructor(
        naziv: string,
        cena: Cena,
        tip: Tip[],
        ocena: Ocena[]
    ) {
        this.naziv = naziv
        this.cena = cena
        this.tip = tip
        this.ocena = ocena
    }
}