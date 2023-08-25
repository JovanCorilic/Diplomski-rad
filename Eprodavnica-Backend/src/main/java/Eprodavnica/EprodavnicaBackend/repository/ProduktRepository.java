package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface ProduktRepository extends JpaRepository<Produkt,Integer> {
    Optional<Produkt>findBySerijskiBroj(String id);
    Boolean existsProduktBySerijskiBroj(String id);

    Page<Produkt>findByNazivContainingIgnoreCaseOrderByDatumPravljenja(String naziv,Pageable pageable);

    Page<Produkt> findByCenaIsBetweenOrderByDatumPravljenja(double od, double do1,Pageable pageable);

    Page<Produkt>findByListaTipovaInOrderByDatumPravljenja(List<Tip>lista,Pageable pageable);

    Page<Produkt>findByOcenaPunBrojInOrderByDatumPravljenja(List<Integer>ocene,Pageable pageable);

    Page<Produkt>findByNazivContainingIgnoreCaseOrCenaIsBetweenOrListaTipovaInOrOcenaPunBrojInOrderByDatumPravljenja(
            Pageable pageable,String naziv, double od, double do1,List<Tip>lista,List<Integer>ocene
    );
    /*@Query("SELECT p FROM Produkt p " +
            "WHERE ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( :lista is null )" +
            "AND ( :ocene is null or p.ocenaPunBroj IN :ocene )")
    Page<Produkt>findByCustomCriteria(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,@Param("ocene") List<Integer>ocene, Pageable pageable
    );*/

}
