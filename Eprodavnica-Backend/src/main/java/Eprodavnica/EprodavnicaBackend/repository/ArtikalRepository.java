package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Artikal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtikalRepository extends JpaRepository<Artikal,Integer> {
}
