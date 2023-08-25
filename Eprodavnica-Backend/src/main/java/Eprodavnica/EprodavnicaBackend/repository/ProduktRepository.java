package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ProduktRepository extends JpaRepository<Produkt,Integer> {
    Optional<Produkt>findBySerijskiBroj(String id);
    Boolean existsProduktBySerijskiBroj(String id);
/*
    Page<Produkt>findByNazivContainingIgnoreCaseOrderByDatumPravljenja(Pageable pageable,String naziv);

    Page<Produkt> findByCenaIsBetweenOrderByDatumPravljenja(Pageable pageable, double od, double do1);

    Page<Produkt>findByListaTipovaOrderByDatumPravljenja(Pageable pageable,List<Tip>lista);

    Page<Produkt>findByOcenaPunBrojInOrderByDatumPravljenja(Pageable pageable,List<Integer>ocene);*/
//findByNazivContainingIgnoreCaseAndCenaIsBetweenAndListaTipovaAndOcenaPunBrojInOrderByDatumPravljenja
    @Query("SELECT p FROM Produkt p " +
            "WHERE ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )" +
            "AND ( :od = -1 or :do1 = -1 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( :lista is null or p.listaTipova IN :lista )" +
            "AND ( :ocene is null or p.ocenaPunBroj IN :ocene )" +
            "ORDER BY p.datumPravljenja")
    Page<Produkt>findByCustomCriteria(
            String naziv, double od, double do1,List<Tip>lista,List<Integer>ocene, Pageable pageable
    );

}
