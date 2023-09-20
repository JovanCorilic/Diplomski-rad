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
    private Integer id;

    @Column
    private String naziv;

    @ManyToMany(mappedBy = "listaTipova")
    private List<Produkt>listaProdukata;

    @ManyToMany(mappedBy = "listaTipova")
    private List<Korisnik>listaKorisnika;

    public Tip(String naziv) {
        this.naziv = naziv;
    }

}
