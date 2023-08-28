package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecenzijaRepository extends JpaRepository<Recenzija,Integer> {

    Page<Recenzija>findByProdukt(Produkt produkt,Pageable pageable);

    Page<Recenzija>findByOcenaInAndProdukt(List<Integer>ocene, Produkt produkt, Pageable pageable);
}
