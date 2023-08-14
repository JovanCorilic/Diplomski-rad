package Eprodavnica.EprodavnicaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecenzijaDTO {
    private Integer id;
    private int ocena;
    private String komentar;
    private String emailMustarija;
    private String serijskiBrojProdukt;
}
