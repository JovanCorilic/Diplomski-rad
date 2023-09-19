package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RecenzijaRepository extends JpaRepository<Recenzija,Integer> {

    Recenzija findByMusterijaAndProdukt(Korisnik korisnik, Produkt produkt);

    @Query("SELECT AVG(r.ocena) FROM Recenzija r " +
            "WHERE ( r.produkt = :produkt )"
    )
    Double dajProsecnuOcenu(@Param("produkt")Produkt produkt);

    Integer countByProdukt(Produkt produkt);

    Boolean existsByMusterijaAndProdukt(Korisnik korisnik,Produkt produkt);

    Page<Recenzija>findByProduktOrderByDatumPravljenjaDesc(Produkt produkt,Pageable pageable);

    Page<Recenzija>findByOcenaInAndProduktOrderByDatumPravljenjaDesc(List<Integer>ocene, Produkt produkt, Pageable pageable);

    Page<Recenzija>findByMusterijaOrderByDatumPravljenjaDesc(Korisnik korisnik,Pageable pageable);

    @Query("SELECT r FROM Recenzija r " +
            "WHERE ( COALESCE( :ocene , null ) is null or r.ocena IN :ocene )" +
            "AND ( CAST( :odDatum AS date ) is null or CAST( :doDatum AS date ) is null or r.datumPravljenja BETWEEN :odDatum AND :doDatum)"+
            "AND ( r.musterija = :korisnik )"+
            "ORDER BY r.datumPravljenja DESC "
    )
    Page<Recenzija>findByCustomCriteria(@Param("ocene") List<Integer>ocene, @Param("odDatum") Date odDatum,
                                        @Param("doDatum")Date doDatum,@Param("korisnik") Korisnik korisnik,Pageable pageable);

    @Query("SELECT r FROM Recenzija r " +
            "WHERE ( COALESCE( :ocene , null ) is null or r.ocena IN :ocene )" +
            "AND ( CAST( :odDatum AS date ) is null or CAST( :doDatum AS date ) is null or r.datumPravljenja BETWEEN :odDatum AND :doDatum)"+
            "ORDER BY r.datumPravljenja DESC "
    )
    Page<Recenzija>findByCustomCriteriaAdmin(@Param("ocene") List<Integer>ocene, @Param("odDatum") Date odDatum,
                                        @Param("doDatum")Date doDatum,Pageable pageable);
}
