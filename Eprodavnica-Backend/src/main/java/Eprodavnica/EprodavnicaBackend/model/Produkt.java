package Eprodavnica.EprodavnicaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produkt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String naziv;

    @Column
    private String deskripcija;

    @Column
    private String serijskiBroj;

    @Column
    private double cena;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tip>listaTipova;

    @OneToMany(mappedBy = "produkt", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Artikal>artikals;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private Korisnik prodavac;

    public Produkt(Integer id, String naziv, String deskripcija, String serijskiBroj, double cena) {
        this.id = id;
        this.naziv = naziv;
        this.deskripcija = deskripcija;
        this.serijskiBroj = serijskiBroj;
        this.cena = cena;
    }
}
