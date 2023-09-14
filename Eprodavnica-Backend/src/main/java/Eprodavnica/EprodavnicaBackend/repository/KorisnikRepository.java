package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.dto.KorisnikDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Uloga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KorisnikRepository extends JpaRepository<Korisnik,Integer> {
    Korisnik findByEmail(String email);

    Boolean existsKorisnikByEmail(String email);
    List<Korisnik> findAllByUlogeContainingAndPotvrdjenIsTrueAndOdobrenOdAdminaIsFalse(Uloga uloga);

    Page<Korisnik>findByUlogeContainingAndPotvrdjenIsTrueAndOdobrenOdAdminaIsTrue(Uloga uloga, Pageable pageable);

    @Query("SELECT k FROM Korisnik k " +
            "WHERE ( :uloga IN k.uloge )" +
            "AND k.potvrdjen is TRUE AND k.odobrenOdAdmina is TRUE " +
            "AND ( :#{#korisnik.email} is null or k.email = :#{#korisnik.email})"+
            "AND ( :#{#korisnik.ime} is null or k.ime = :#{#korisnik.ime})"+
            "AND ( :#{#korisnik.prezime} is null or k.prezime = :#{#korisnik.prezime})"
    )
    Page<Korisnik>findByCustomCriteria(@Param("korisnik") KorisnikDTO korisnik, @Param("uloga")Uloga uloga, Pageable pageable);

    Boolean existsKorisnikByEmailAndIstorijaKupljenihProdukataContains(String email, Produkt produkt);
}
