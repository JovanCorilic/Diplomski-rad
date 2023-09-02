package Eprodavnica.EprodavnicaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
    private double ocena;
    private Date datumPravljenja;
    private int akcija;
    private List<TipDTO>listaTipova;
    private String emailProdavac;
    //private KorisnikDTO prodavac;
}
