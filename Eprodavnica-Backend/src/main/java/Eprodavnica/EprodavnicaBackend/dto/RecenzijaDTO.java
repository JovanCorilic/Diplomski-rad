package Eprodavnica.EprodavnicaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecenzijaDTO {
    private Integer id;
    private int ocena;
    private String komentar;
    private Date datumPravljenja;
    private String emailMustarija;
    private ProduktMiniDTO produkt;
}
