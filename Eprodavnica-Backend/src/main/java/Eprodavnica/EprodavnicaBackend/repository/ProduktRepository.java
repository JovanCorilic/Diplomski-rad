package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Korisnik;
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

    @Query("SELECT p FROM Produkt p " +
            "WHERE ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or :lista member of p.listaTipova )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )")
    Page<Produkt>findByCustomCriteria(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,@Param("ocene") List<Integer>ocene, Pageable pageable
    );

    Page<Produkt>findByIstorijaKupacaContains(Korisnik korisnik,Pageable pageable);

    @Query("SELECT p FROM Produkt p " +
            "WHERE ( :korisnik member of p.istorijaKupaca )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or :lista member of p.listaTipova )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )" +
            "AND ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )")
    Page<Produkt>findByCustomCriteriaIstorijaProdukata(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,
            @Param("ocene") List<Integer>ocene, @Param("korisnik") Korisnik korisnik, Pageable pageable
    );

    Page<Produkt>findByWishlistContains(Korisnik korisnik, Pageable pageable);

    @Query("SELECT p FROM Produkt p " +
            "WHERE ( :korisnik member of p.wishlist )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or :lista member of p.listaTipova )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )" +
            "AND ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )")
    Page<Produkt>findByCustomCriteriaWishlist(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,
            @Param("ocene") List<Integer>ocene, @Param("korisnik") Korisnik korisnik, Pageable pageable
    );

    Page<Produkt>findByProdavac(Korisnik korisnik, Pageable pageable);

    @Query("SELECT p FROM Produkt p " +
            "WHERE ( :korisnik = p.prodavac )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or :lista member of p.listaTipova )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )" +
            "AND ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )")
    Page<Produkt>findByCustomCriteriaProdavac(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,
            @Param("ocene") List<Integer>ocene, @Param("korisnik") Korisnik korisnik, Pageable pageable);

    /*
    Page<Produkt>findByNazivContainingIgnoreCaseOrderByDatumPravljenja(String naziv,Pageable pageable);

    Page<Produkt> findByCenaIsBetweenOrderByDatumPravljenja(double od, double do1,Pageable pageable);

    Page<Produkt>findByListaTipovaInOrderByDatumPravljenja(List<Tip>lista,Pageable pageable);

    Page<Produkt>findByOcenaPunBrojInOrderByDatumPravljenja(List<Integer>ocene,Pageable pageable);

    Page<Produkt>findByNazivContainingIgnoreCaseOrCenaIsBetweenOrListaTipovaInOrOcenaPunBrojInOrderByDatumPravljenja(
            Pageable pageable,String naziv, double od, double do1,List<Tip>lista,List<Integer>ocene
    );
    */
}
