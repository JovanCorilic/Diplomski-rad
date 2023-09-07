import { Korisnik } from "../Korisnik";
import { Tip } from "../Tip";
import { Cena } from "./Cena";
import { Datum } from "./Datum";
import { Ocena } from "./Ocena";

export class Filter {
    naziv: string
    cena: Cena
    tip: Tip[]
    ocena: Ocena[]
    datum: Datum
    constructor(
        naziv: string,
        cena: Cena,
        tip: Tip[],
        ocena: Ocena[],
        datum: Datum
    ) {
        this.naziv = naziv
        this.cena = cena
        this.tip = tip
        this.ocena = ocena
        this.datum = datum
    }
}