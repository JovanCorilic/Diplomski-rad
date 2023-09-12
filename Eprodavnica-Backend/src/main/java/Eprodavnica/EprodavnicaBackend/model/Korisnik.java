package Eprodavnica.EprodavnicaBackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Korisnik implements UserDetails {

    @Id
    //@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable=false)
    private String ime;

    @Column(nullable=false)
    private String prezime;

    @Column(nullable=false,unique = true)
    private String email;

    @Column(nullable=false)
    private String lozinka;

    @Column(nullable = false)
    private boolean potvrdjen;

    @Column(nullable = false)
    private boolean odobrenOdAdmina;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Uloga> uloge;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tip>listaTipova;

    @OneToMany(mappedBy = "musterija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Racun>listaRacuna;

    @OneToMany(mappedBy = "musterija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recenzija>listaRecenzija;

    @OneToMany(mappedBy = "prodavac", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Produkt>listaProdukata;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Produkt>wishlist;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Produkt>istorijaKupljenihProdukata;

    public Korisnik(Integer id, String ime, String prezime, String email, String lozinka) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
    }

    public Korisnik(String ime, String prezime, String email, String lozinka, boolean potvrdjen, boolean odobrenOdAdmina, List<Uloga> uloge) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.potvrdjen = potvrdjen;
        this.odobrenOdAdmina = odobrenOdAdmina;
        this.uloge = uloge;
    }

    public Korisnik(String ime, String prezime, String email, String lozinka) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
    }

    public Korisnik(String ime, String prezime, String email,Boolean odobrenOdAdmina) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.odobrenOdAdmina=odobrenOdAdmina;
    }

    public Korisnik(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> permissions = new ArrayList<>(20);
        for (Uloga role : this.uloge) {
            permissions.addAll(role.getPrivilegijes());
        }
        return permissions;
    }

    @Override
    public String getPassword() {
        return this.lozinka;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
