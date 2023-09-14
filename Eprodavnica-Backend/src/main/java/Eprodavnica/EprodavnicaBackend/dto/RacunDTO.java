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
public class RacunDTO {
    private double konacnaCena;
    private String brojRacuna;
    private String emailMusterija;
    private Date datumKreiranja;
    private boolean korpa;
}
