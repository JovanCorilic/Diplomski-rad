package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Tip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipRepository extends JpaRepository<Tip,Integer> {
    Optional<Tip>findByNaziv(String id);
    Boolean existsTipByNaziv(String id);

    List<Tip>findByNazivIgnoreCaseIn(List<String>lista);

    Page<Tip>findByNazivContainingIgnoreCaseOrderByNazivAsc(String naziv, Pageable pageable);

}
