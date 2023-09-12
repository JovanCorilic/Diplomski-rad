package Eprodavnica.EprodavnicaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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

    @Column
    private int akcija;

    @Column
    private int brojProdato;

    @Column boolean odobrenOdAdmina;

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

    @ManyToMany(mappedBy = "wishlist")
    private List<Korisnik>wishlist;

    @ManyToMany(mappedBy = "istorijaKupljenihProdukata")
    private List<Korisnik>istorijaKupaca;

    public Produkt(String naziv, String deskripcija, String serijskiBroj, double cena, double ocena, Date datumPravljenja, int akcija,int brojProdato, Boolean odobrenOdAdmina,List<Tip> listaTipova, Korisnik prodavac) {
        this.naziv = naziv;
        this.deskripcija = deskripcija;
        this.serijskiBroj = serijskiBroj;
        this.cena = cena;
        this.ocena = ocena;
        this.datumPravljenja = datumPravljenja;
        this.akcija = akcija;
        this.brojProdato = brojProdato;
        this.listaTipova = listaTipova;
        this.prodavac = prodavac;
        this.odobrenOdAdmina=odobrenOdAdmina;
    }

    public Produkt(String naziv, String serijskiBroj, double cena, double ocena, int akcija,Boolean odobrenOdAdmina) {
        this.naziv = naziv;
        this.serijskiBroj = serijskiBroj;
        this.cena = cena;
        this.ocena = ocena;
        this.akcija = akcija;
        this.odobrenOdAdmina = odobrenOdAdmina;
    }

    public Produkt(String serijskiBroj) {
        this.serijskiBroj = serijskiBroj;
    }

    /*public void IzracunajProsecnuOcenu(){
        double temp = 0.0;
        for (Recenzija recenzija : listaRecenzija){
            temp += recenzija.getOcena();
        }
        ocena = temp/listaRecenzija.size();
    }*/

    public void PretvoriUPunBroj(){
        ocenaPunBroj = (int)ocena;
    }


}
