package Eprodavnica.EprodavnicaBackend.dto;

import Eprodavnica.EprodavnicaBackend.model.Produkt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdavacDTO {
    private String ime;
    private String prezime;
    private String email;
    private List<ProduktDTO>listaProdukata;
}
