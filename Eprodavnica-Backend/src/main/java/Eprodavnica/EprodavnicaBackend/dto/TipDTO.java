package Eprodavnica.EprodavnicaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipDTO {
    private String naziv;
    private int brojPojavljivanja;
}
