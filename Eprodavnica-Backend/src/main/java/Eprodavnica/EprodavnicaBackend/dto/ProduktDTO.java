package Eprodavnica.EprodavnicaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProduktDTO {
    private String naziv;
    private String deskripcija;
    private String serijskiBroj;
    private double cena;
    private List<TipDTO>listaTipova;
    //private KorisnikDTO prodavac;
}
