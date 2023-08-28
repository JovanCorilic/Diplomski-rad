package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RacunRepository extends JpaRepository<Racun,Integer> {
    Optional<Racun>findByBrojRacuna(String id);
    Boolean existsRacunByBrojRacuna(String id);

    Optional<Racun>findByKonacnaCenaAndMusterija(double broj, Korisnik musterija);
}
