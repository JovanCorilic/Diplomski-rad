import { Ocena } from "./Ocena";

export class Filter {
    ocena: Ocena[]
    constructor(ocena: Ocena[]) {
        this.ocena = ocena
    }
}