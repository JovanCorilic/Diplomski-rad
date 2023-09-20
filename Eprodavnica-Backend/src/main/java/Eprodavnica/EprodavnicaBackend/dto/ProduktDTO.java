package Eprodavnica.EprodavnicaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    private int brojProdato;
    private List<TipDTO>listaTipova;
    private String emailProdavac;
    private boolean odobrenOdAdmina;
    private boolean odobrenOdProdavca;
    private ImageModel slika;
    //private KorisnikDTO prodavac;
}
