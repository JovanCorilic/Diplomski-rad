package Eprodavnica.EprodavnicaBackend.dto.Filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {
    private String naziv;
    private CenaDTO cena;
    private List<TipFilterDTO>tip;
    private List<OcenaDTO>ocena;
    private DatumDTO datum;
}
