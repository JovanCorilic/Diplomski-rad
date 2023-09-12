package Eprodavnica.EprodavnicaBackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtikalDTO {
    private Integer id;
    private Integer broj;
    private String nazivProdukta;
    private Double cena;
    private Integer akcija;
    private Double ukupnaCena;
    private String serijskiBroj;
}
