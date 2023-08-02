package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduktRepository extends JpaRepository<Produkt,Integer> {
    Optional<Produkt>findBySerijskiBroj(String id);
}
