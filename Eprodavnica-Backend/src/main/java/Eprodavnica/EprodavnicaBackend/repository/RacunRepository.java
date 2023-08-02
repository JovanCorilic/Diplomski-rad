package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Racun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RacunRepository extends JpaRepository<Racun,Integer> {
    Optional<Racun>findByBrojRacuna(String id);
}
