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

    Page<Produkt>findAllByOdobrenOdAdminaIsTrueAndOdobrenOdProdavcaIsTrueAndProdavacOdobrenOdAdminaIsTrueOrderByDatumPravljenjaDesc(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Produkt p " +
            "JOIN p.listaTipova tip " +
            "WHERE ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or tip IN :lista )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )" +
            "AND p.odobrenOdAdmina is TRUE AND p.odobrenOdProdavca IS TRUE AND p.prodavac.odobrenOdAdmina IS TRUE "+
            "ORDER BY p.datumPravljenja DESC "
    )
    Page<Produkt>findByCustomCriteria(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,@Param("ocene") List<Integer>ocene, Pageable pageable
    );

    Page<Produkt>findByIstorijaKupacaContainsAndProdavacOdobrenOdAdminaIsTrueOrderByDatumPravljenjaDesc(Korisnik korisnik,Pageable pageable);

    @Query("SELECT DISTINCT p FROM Produkt p " +
            "JOIN p.listaTipova tip " +
            "WHERE ( :korisnik member of p.istorijaKupaca )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or tip IN :lista )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )" +
            "AND ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )"+
            "AND p.prodavac.odobrenOdAdmina IS TRUE "+
            "ORDER BY p.datumPravljenja DESC "
    )
    Page<Produkt>findByCustomCriteriaIstorijaProdukata(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,
            @Param("ocene") List<Integer>ocene, @Param("korisnik") Korisnik korisnik, Pageable pageable
    );

    Page<Produkt>findByWishlistContainsAndProdavacOdobrenOdAdminaIsTrueOrderByDatumPravljenjaDesc(Korisnik korisnik, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Produkt p " +
            "JOIN p.listaTipova tip " +
            "WHERE ( :korisnik member of p.wishlist )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or tip IN :lista )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )" +
            "AND ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )"+
            "AND p.prodavac.odobrenOdAdmina IS TRUE "+
            "ORDER BY p.datumPravljenja DESC "
    )
    Page<Produkt>findByCustomCriteriaWishlist(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,
            @Param("ocene") List<Integer>ocene, @Param("korisnik") Korisnik korisnik, Pageable pageable
    );

    Page<Produkt>findByProdavacOrderByDatumPravljenjaDesc(Korisnik korisnik, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Produkt p " +
            "JOIN p.listaTipova tip " +
            "WHERE ( :korisnik = p.prodavac )" +
            "AND ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or tip IN :lista )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )" +
            "AND ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )"+
            "ORDER BY p.datumPravljenja DESC "
    )
    Page<Produkt>findByCustomCriteriaProdavac(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,
            @Param("ocene") List<Integer>ocene, @Param("korisnik") Korisnik korisnik, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Produkt p " +
            "JOIN p.listaTipova tip " +
            "WHERE ( :od = -1.0 or :do1 = -1.0 or p.cena BETWEEN :od AND :do1 )" +
            "AND ( COALESCE( :lista , null ) is null or tip IN :lista )" +
            "AND ( COALESCE( :ocene , null ) is null or p.ocenaPunBroj IN :ocene )" +
            "AND ( :naziv is null or LOWER(p.naziv) LIKE LOWER(CONCAT('%', :naziv, '%')) )"+
            "ORDER BY p.datumPravljenja DESC "
    )
    Page<Produkt>findByCustomCriteriaAdmin(
            @Param("naziv") String naziv,@Param("od") double od,@Param("do1") double do1,@Param("lista") List<Tip>lista,
            @Param("ocene") List<Integer>ocene, Pageable pageable);

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
