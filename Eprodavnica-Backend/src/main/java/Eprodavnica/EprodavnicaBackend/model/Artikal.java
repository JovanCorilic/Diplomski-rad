package Eprodavnica.EprodavnicaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Artikal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int broj;

    @Column
    private String nazivProdukta;

    @Column
    private Double cena;

    @Column
    private Integer akcija;

    @Column
    private Double ukupnaCena;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private Produkt produkt;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private Racun racun;

    public Artikal(Integer id, int broj, String nazivProdukta, Double cena, Integer akcija, Double ukupnaCena) {
        this.id = id;
        this.broj = broj;
        this.nazivProdukta = nazivProdukta;
        this.cena = cena;
        this.akcija = akcija;
        this.ukupnaCena = ukupnaCena;
    }
}
