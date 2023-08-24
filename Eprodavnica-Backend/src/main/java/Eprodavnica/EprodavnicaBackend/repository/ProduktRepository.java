package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProduktRepository extends JpaRepository<Produkt,Integer> {
    Optional<Produkt>findBySerijskiBroj(String id);
    Boolean existsProduktBySerijskiBroj(String id);

    List<Produkt> findByCenaIsGreaterThanEqualAndCenaIsLessThanEqual(double od, double do1);

    List<Produkt>findByListaTipovaContaining(List<Tip>lista);
}
