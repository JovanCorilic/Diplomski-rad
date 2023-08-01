package Eprodavnica.EprodavnicaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String naziv;

    @Column
    private int brojPojavljivanja;

    @ManyToMany(mappedBy = "listaTipova")
    private List<Produkt>listaArtikala;

    @ManyToMany(mappedBy = "listaTipova")
    private List<Korisnik>listaKorisnika;

    public Tip(String naziv) {
        this.naziv = naziv;
    }

    public Tip(String naziv, int brojPojavljivanja) {
        this.naziv = naziv;
        this.brojPojavljivanja = brojPojavljivanja;
    }
}
