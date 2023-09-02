package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Artikal;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtikalRepository extends JpaRepository<Artikal,Integer> {
    Page<Artikal>findByRacun(Racun racun, Pageable pageable);
}
