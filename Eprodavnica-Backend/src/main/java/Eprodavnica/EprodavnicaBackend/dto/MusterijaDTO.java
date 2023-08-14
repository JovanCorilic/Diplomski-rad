package Eprodavnica.EprodavnicaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusterijaDTO {
    private String ime;
    private String prezime;
    private String email;
    private String lozinka;
    private List<TipDTO>listaTipova;
    private List<RacunDTO>listaRacuna;
    private List<RecenzijaDTO>listaRecenzija;

    public boolean proveraPodataka(){
        if (ime.isEmpty() || prezime.isEmpty() || email.isEmpty() || lozinka.isEmpty())
            return true;
        if (!lozinka.matches(".*\\d+.*") || !lozinka.matches(".*[^a-zA-Z0-9 ].*")){
            return true;
        }
        return false;
    }

    public MusterijaDTO(String ime, String prezime, String email, String lozinka) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
    }
}
