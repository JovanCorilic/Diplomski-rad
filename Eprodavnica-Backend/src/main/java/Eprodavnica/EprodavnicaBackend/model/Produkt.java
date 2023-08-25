package Eprodavnica.EprodavnicaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
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

    @Column
    private double ocena;

    @Column
    private int ocenaPunBroj;

    @Column
    private Date datumPravljenja;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tip>listaTipova;

    @OneToMany(mappedBy = "produkt", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Artikal>artikals;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private Korisnik prodavac;

    @OneToMany(mappedBy = "produkt", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recenzija>listaRecenzija;

    public Produkt(String naziv, String deskripcija, String serijskiBroj, double cena, double ocena, List<Tip> listaTipova, Korisnik prodavac, List<Recenzija> listaRecenzija) {
        this.naziv = naziv;
        this.deskripcija = deskripcija;
        this.serijskiBroj = serijskiBroj;
        this.cena = cena;
        this.ocena = ocena;
        this.listaTipova = listaTipova;
        this.prodavac = prodavac;
        this.listaRecenzija = listaRecenzija;
    }

    public Produkt(String serijskiBroj) {
        this.serijskiBroj = serijskiBroj;
    }

    public void IzracunajProsecnuOcenu(){
        double temp = 0.0;
        for (Recenzija recenzija : listaRecenzija){
            temp += recenzija.getOcena();
        }
        ocena = temp/listaRecenzija.size();
    }

    public void PretvoriUPunBroj(){
        ocenaPunBroj = (int)ocena;
    }
}
