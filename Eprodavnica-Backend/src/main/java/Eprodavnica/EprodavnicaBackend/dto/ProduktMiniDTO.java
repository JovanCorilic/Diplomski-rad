package Eprodavnica.EprodavnicaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProduktMiniDTO {
    private String naziv;
    private String serijskiBroj;
    private double cena;
    private double ocena;
    private int akcija;
    private boolean odobrenOdAdmina;
    private boolean odobrenOdProdavca;
    private ImageModel slika;
}
