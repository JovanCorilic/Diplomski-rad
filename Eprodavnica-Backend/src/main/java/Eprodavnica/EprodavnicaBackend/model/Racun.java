package Eprodavnica.EprodavnicaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Racun {
    @Id
    private Integer id;

    @Column
    private double konacnaCena;

    @Column
    private String brojRacuna;

    @Column
    private Date datumKreiranja;

    @Column
    private boolean korpa;

    @OneToMany(mappedBy = "racun", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Artikal>artikals;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private Korisnik musterija;

    public Racun(double konacnaCena, String brojRacuna, Date datumKreiranja, boolean korpa, Korisnik musterija) {
        this.konacnaCena = konacnaCena;
        this.brojRacuna = brojRacuna;
        this.datumKreiranja = datumKreiranja;
        this.korpa = korpa;
        this.musterija = musterija;
    }
}
