package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Uloga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KorisnikRepository extends JpaRepository<Korisnik,Integer> {
    Korisnik findByEmail(String email);

    Boolean existsKorisnikByEmail(String email);
    List<Korisnik> findAllByUlogeContainingAndPotvrdjenIsTrueAndOdobrenOdAdminaIsFalse(Uloga uloga);
}
